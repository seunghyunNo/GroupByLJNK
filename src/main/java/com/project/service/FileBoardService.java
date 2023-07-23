package com.project.service;

import com.project.domain.Board;
import com.project.domain.Recommend;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileBoardService{
    int write(Board board, Map<String, MultipartFile> files,String appId);

    List<Board> list();


    List<Board> list(Model model, Integer page, String appId);

    Board findById(Long id);

    int update(Map<String,MultipartFile> files, Board board,Long[] deletefiles);

    int deleteById(Long id);

    int countCheck(Long userId,Long boardId,Long count);


}
