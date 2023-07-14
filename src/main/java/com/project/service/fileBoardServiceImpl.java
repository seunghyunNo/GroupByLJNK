package com.project.service;

import com.project.domain.Attachment;
import com.project.domain.Board;
import com.project.repository.AttachmentRepository;
import com.project.repository.fileBoardRepository;
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
import java.util.List;
import java.util.Map;
@Service
public class fileBoardServiceImpl implements fileBoardService  {

    private fileBoardRepository fileRepository;
    private AttachmentRepository attachmentRepository;


    @Value("{app.upload.path}")
    private String uploadDirectory;

    @Autowired
    public fileBoardServiceImpl(SqlSession sqlSession) {
        fileRepository = sqlSession.getMapper(fileBoardRepository.class);
        attachmentRepository = sqlSession.getMapper(AttachmentRepository.class);
    }

    @Override
    public int write(Board board, Map<String, MultipartFile> files) {
        // 작성자 TODO

        int result = fileRepository.write(board);
        insertFiles(files, board.getId());

        return result;
    }

    public void insertFiles(Map<String,MultipartFile> files, Long id)
    {
        if(files != null)
        {
            for(var entry: files.entrySet())
            {
                if(!entry.getKey().startsWith("upload"))
                {
                    continue;
                }

                Attachment file = uploadFile(entry.getValue());

                if(file != null)
                {
                    file.setBoard_id(id);
                    attachmentRepository.saveFile(file);
                }
            }
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
                Files.copy(multipartFile.getInputStream(),
                        copyRoute, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            attachment = Attachment.builder()
                    .sourcename(sourceName)
                    .filename(fileName)
                    .build();

            return  attachment;

    }

    @Override
    public List<Board> list() {
        return fileRepository.list();
    }

    @Override
    public List<Board> list(Model model, Integer page) {

        // TODO

        return null;
    }

    @Override
    public int update(Map<String, MultipartFile> files, Board board, Long[] deleteFiles) {
        int result = fileRepository.update(board);

        insertFiles(files, board.getId());

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

        Board board = fileRepository.searchById(id);

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
            result = fileRepository.delete(board);
        }

        return result;
    }
}
