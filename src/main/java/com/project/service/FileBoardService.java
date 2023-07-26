package com.project.service;

import com.project.domain.FileBoard;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileBoardService{
    int write(FileBoard fileBoard, Map<String, MultipartFile> files, String appId);

    List<FileBoard> list(String appId);


    List<FileBoard> list(Model model, Integer page, String appId);

    FileBoard findById(Long id);

    int update(Map<String,MultipartFile> files, FileBoard fileBoard, Long[] deletefiles);

    int deleteById(Long id);


    int donwloadCount(Long id);

}
