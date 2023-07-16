package com.project.service;

import com.project.domain.Board;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface fileBoardService {
    int write(Board board, Map<String, MultipartFile> files);

    List<Board> list();

    List<Board> list(Model model, Integer page);

    Board findById(Long id);

    int update(Map<String,MultipartFile> files, Board board,Long[] deletefiles);

    int delete(Long id);
}
