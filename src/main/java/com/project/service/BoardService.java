package com.project.service;

import com.project.domain.Board;
import org.springframework.transaction.annotation.Transactional;

public interface BoardService {
    int write(Board board);



    @Transactional
    Board detail(Long id);

        //list 해결 해야됨
//    List<Board> list();
//
//    List<Board> list(Integer page, Model model);

    Board selectById(Long id);

    int update(Board board);

    int deleteById(Long id);
}
