package com.project.repository;

import com.project.domain.Board;



import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository {

    // 새글 작성
    int write(Board board);

    // 전체 글 목록
    List<Board> list();

    // id 글 내용 읽기
    Board findById(Long id);

    // 작성일
    LocalDateTime regdate(Long id);

    // 조회수
    int viewCnt(Long id);

    // 특정 id 글 수정
    int update(Board board);

    // 특정 id 글 삭제
    int delete(Board board);


    // 페이징
    int countAll(String appId);

    // 게시글 출력 갯수
    List<Board> selectByPage(int from, int rows, String appId);


    Long recommendCount(Long id);
}
