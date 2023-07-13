package com.project.repository;

import com.project.domain.Board;

import java.time.LocalDateTime;
import java.util.List;

public interface boardRepository {

    // 새글작성
    int write(Board board);

    List<Board> list();


    // 확실하지 않음
    int regDate(LocalDateTime regdate);



    int recommendCnt(Long id);

    int viewCnt(Long id);

    int update(Board board);


    List<Board> selectFromRow(int from, int rows);




}
