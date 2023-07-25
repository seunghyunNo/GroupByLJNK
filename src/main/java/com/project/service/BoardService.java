package com.project.service;

import com.project.domain.Board;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

public interface BoardService {
    int write(Board board);



    @Transactional
    Board detail(Long id);


    List<Board> list();

    List<Board> list(Integer page, Model model, String appId);

    Board selectById(Long id);

    int update(Board board);

    int deleteById(Long id);
}