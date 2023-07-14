package com.project.repository;

import com.project.domain.Board;

import java.util.List;

public interface fileBoardRepository {
    int write(Board board);
    List<Board> list();

    Board searchById(Long id);

    int update(Board board);

    int delete(Board board);

    List<Board> selectByPage(int from,int row);

}
