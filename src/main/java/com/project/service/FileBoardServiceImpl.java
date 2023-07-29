package com.project.service;

import com.project.domain.Attachment;
import com.project.domain.FileBoard;
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
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

    private RecommendRepository recommendRepository;


    private UserRepository userRepository;


    @Autowired
    public FileBoardServiceImpl(SqlSession sqlSession) {
        fileBoardRepository = sqlSession.getMapper(FileBoardRepository.class);
        attachmentRepository = sqlSession.getMapper(AttachmentRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
        recommendRepository = sqlSession.getMapper(RecommendRepository.class);
    }

    @Override
    public int write(FileBoard fileBoard, Map<String, MultipartFile> files, String appId) {
        User user = Util.getLoggedUser();

        user = userRepository.findByUsername(user.getUsername());
        fileBoard.setUser(user);
        fileBoard.setGame_id(appId);

        int result = fileBoardRepository.write(fileBoard);
        insertFiles(files, fileBoard);

        return result;
    }

    public void insertFiles(Map<String,MultipartFile> files, FileBoard fileBoard)
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

                if(file != null)
                {
                    fileList.add(file);
                    file.setBoard_id(fileBoard.getId());
                    attachmentRepository.saveFile(file);
                }
            }
            fileBoard.setFileList(fileList);
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
    public List<FileBoard> list(String appId) {
        return fileBoardRepository.list(appId);
    }

    @Override
    public List<FileBoard> list(Model model, Integer page, String appId) {

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

        long count = fileBoardRepository.countAll(appId);

        int totalPage = (int)Math.ceil(count/(double)pageRows);

        if(page > totalPage)
        {
            page = totalPage;
        }
        int fromRow = (page - 1) * pageRows;;

       if(page == 0)
        {
            fromRow = 0;
        }

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

        List<FileBoard> list = fileBoardRepository.selectByPage(fromRow,pageRows,appId);

        for(int i = 0; i < list.size() ; i++)
        {
           Long id = list.get(i).getId();
           FileBoard fileBoard = fileBoardRepository.searchById(id);
            if(fileBoard != null)
            {
                List<Attachment> files = attachmentRepository.findByFileBoard(id);
                fileBoard.setFileList(files);
                fileBoard.setRecommend(recommendRepository.countByBoardId(id));
            }
            list.set(i,fileBoard);
        }

        model.addAttribute("list",list);
        return list;
    }

    @Override
    public FileBoard findById(Long id) {
        FileBoard fileBoard = fileBoardRepository.searchById(id);
        if(fileBoard != null)
        {
            List<Attachment> files = attachmentRepository.findByFileBoard(fileBoard.getId());
            fileBoard.setFileList(files);
        }
        return fileBoard;
    }

    @Override
    public int update(Map<String, MultipartFile> files, FileBoard fileBoard, Long[] deleteFiles) {
        int result = fileBoardRepository.update(fileBoard);

        insertFiles(files, fileBoard);

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

        File f= new File(Directory,delFile.getFilename());

        if(f.exists())
        {
            if (f.delete()) { // 삭제!
                System.out.println("삭제 성공");
            } else {
                System.out.println("삭제 실패");
            }
       }
    }

    @Override
    public int deleteById(Long id) {
        int result = 0;

        FileBoard fileBoard = fileBoardRepository.searchById(id);

        if(fileBoard != null)
        {
            List<Attachment> files =attachmentRepository.findByFileBoard(id);

            if(files != null && files.size() >0)
            {
                for(var file : files)
                {
                    deleteFile(file);
                }
            }
            result = fileBoardRepository.delete(fileBoard);
        }

        return result;
    }


    @Override
    public int donwloadCount(Long id) {
        return fileBoardRepository.downloadCount(id);
    }


}
