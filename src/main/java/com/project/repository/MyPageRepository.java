package com.project.repository;

import com.project.domain.Board;
import com.project.domain.FileBoard;

import java.util.List;

public interface MyPageRepository {

    long fileCountAll(long id);

    List<FileBoard> fileSelectByPage(int from, int row, long id);

    long boardCountAll(long id);

    List<Board> boardSelectByPage(int fromRow, int pageRows, long id);

    long recommendCount(long id);
}
