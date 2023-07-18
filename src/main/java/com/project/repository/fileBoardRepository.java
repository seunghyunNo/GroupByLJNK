package com.project.repository;

import com.project.domain.Board;

import java.util.List;

public interface fileBoardRepository {
    int write(Board board);
    List<Board> list();

    int downloadCnt(Long id);

    int recommendCnt(Long id);

    int update(Board board);

    int delete(Board board);

    List<Board> selectBypage(int from,int row);

}
