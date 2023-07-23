package com.project.repository;

import com.project.domain.Board;

import java.util.List;

public interface FileBoardRepository {
    int write(Board board,String appId);
    List<Board> list();

    Board searchById(Long id);

    int update(Board board);

    int delete(Board board);

    List<Board> selectByPage(int from,int row,String appId);

    int countAll();

    int setCount(Long count,Long boardId);
}
