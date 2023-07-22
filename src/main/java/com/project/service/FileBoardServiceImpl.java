package com.project.service;

import com.project.domain.Attachment;
import com.project.domain.Board;
import com.project.domain.Recommend;
import com.project.domain.User;
import com.project.repository.AttachmentRepository;
import com.project.repository.FileBoardRepository;
import com.project.repository.RecommendRepository;
import com.project.repository.UserRepository;
import com.project.util.Util;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class FileBoardServiceImpl implements FileBoardService {



    @Value("${app.upload.path}")
    private String uploadDirectory;

    @Value("${app.pagination.write_pages}")
    private int WRITE_PAGES;

    @Value("${app.pagination.page_rows}")
    private int PAGE_ROWS;

    private FileBoardRepository fileBoardRepository;
    private AttachmentRepository attachmentRepository;


    private UserRepository userRepository;


    @Autowired
    public FileBoardServiceImpl(SqlSession sqlSession) {
        fileBoardRepository = sqlSession.getMapper(FileBoardRepository.class);
        attachmentRepository = sqlSession.getMapper(AttachmentRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
    }

    @Override
    public int write(Board board, Map<String, MultipartFile> files) {
        User user = Util.getLoggedUser();

        user = userRepository.findByUsername(user.getUsername());
        board.setUser(user);

        int result = fileBoardRepository.write(board);
        insertFiles(files, board);

        return result;
    }

    public void insertFiles(Map<String,MultipartFile> files, Board board)
    {

        if(files != null)
        {
            List<Attachment> fileList = new ArrayList<>();
            for(var entry: files.entrySet())
            {
                if(!entry.getKey().startsWith("upfile"))
                {
                    continue;
                }

                Attachment file = uploadFile(entry.getValue());
                System.out.println(file);

                if(file != null)
                {
                    fileList.add(file);
                    file.setBoard_id(board.getId());
                    attachmentRepository.saveFile(file);
                }
            }
            System.out.println(fileList);
            board.setFiles(fileList);
            System.out.println(board.getFiles());
        }
    }

    public Attachment uploadFile(MultipartFile multipartFile)
    {
        Attachment attachment = null;

        String originalFileName = multipartFile.getOriginalFilename();
        if(originalFileName == null || originalFileName.length() == 0)
        {
            return null;
        }

        String sourceName = StringUtils.cleanPath(originalFileName);
        String fileName = sourceName;

        File file = new File(uploadDirectory+File.separator+sourceName);

        if(file.exists()) {
            int position = fileName.lastIndexOf(".");   // 파일 이름 구분하기위한 구분점

            if (position > -1) {
                String name = fileName.substring(0, position);
                String extension = fileName.substring(position + 1); // 확장자

                fileName = name + "_" + System.currentTimeMillis() + "." + extension;
            } else {
                fileName += "_" + System.currentTimeMillis();
            }
        }

            Path copyRoute = Paths.get(new File(uploadDirectory+File.separator+fileName).getAbsolutePath());

            try {
                Files.copy(
                        multipartFile.getInputStream(),
                        copyRoute, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            attachment = Attachment.builder()
                    .filename(fileName)
                    .sourcename(sourceName)
                    .build();

            return  attachment;

    }

    @Override
    public List<Board> list() {
        return fileBoardRepository.list();
    }

    @Override
    public List<Board> list(Model model, Integer page) {

        if(page == null || page < 1)
        {
            page = 1;
        }

        HttpSession session = Util.getSession();
        Integer writePage = (Integer)session.getAttribute("writePage");
        Integer pageRows = (Integer)session.getAttribute("pageRows");
        if(writePage == null)
        {
            writePage = WRITE_PAGES;
        }
        if(pageRows == null)
        {
            pageRows = PAGE_ROWS;
        }

        session.setAttribute("page",page);

        long count = fileBoardRepository.countAll();

        int totalPage = (int)Math.ceil(count/(double)pageRows);

        if(page > totalPage)
        {
            page = totalPage;
        }

        int fromRow = (page - 1) * pageRows;

        int start = (((page -1)/ writePage)*writePage)+1;
        int end = start + writePage - 1;
        if(end >= totalPage) end = totalPage;

        model.addAttribute("count",count);
        model.addAttribute("page",page);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("pageRows",pageRows);

        model.addAttribute("url",Util.getRequest().getRequestURI());
        model.addAttribute("writePage",writePage);
        model.addAttribute("start",start);
        model.addAttribute("end",end);

        List<Board> list = fileBoardRepository.selectByPage(fromRow,pageRows);
        model.addAttribute("list",list);
        System.out.println(list);
        return list;
    }

    @Override
    public Board findById(Long id) {
        Board board = fileBoardRepository.searchById(id);
        if(board != null)
        {
            List<Attachment> files = attachmentRepository.findByFileBoard(board.getId());
            board.setFiles(files);
        }

        return board;
    }

    @Override
    public int update(Map<String, MultipartFile> files, Board board, Long[] deleteFiles) {
        int result = fileBoardRepository.update(board);

        insertFiles(files, board);

        if(deleteFiles != null)
        {
            for(var fileId : deleteFiles)
            {
                Attachment file = attachmentRepository.findByFileId(fileId);
                if(file != null)
                {
                    deleteFile(file);
                    attachmentRepository.delete(file);
                }
            }
        }

        return result;
    }

    public void deleteFile(Attachment delFile)
    {
        String Directory =new File(uploadDirectory).getAbsolutePath();

        File file = new File(Directory,delFile.getFilename());

        if(file.exists())
        {
            file.delete();
        }
    }

    @Override
    public int delete(Long id) {
        int result = 0;

        Board board = fileBoardRepository.searchById(id);

        if(board != null)
        {
            List<Attachment> files =attachmentRepository.findByFileBoard(id);

            if(files != null && files.size() >0)
            {
                for(var file : files)
                {
                    deleteFile(file);
                }
            }
            result = fileBoardRepository.delete(board);
        }

        return result;
    }



}
