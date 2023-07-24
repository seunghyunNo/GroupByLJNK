//package com.project.repository;
//
//import com.project.domain.FileBoard;
//
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//public interface BoardRepository {
//
//    // 새글 작성
//    int write(FileBoard fileBoard);
//
//    // 전체 글 목록
//    List<FileBoard> list();
//
//    // id 글 내용 읽기
//    FileBoard findById(Long id);
//
//    // 작성일
//    LocalDateTime regDate(Long id);
//
//    // 조회수
//    int viewCnt(Long id);
//
//    // 특정 id 글 수정
//    int update(FileBoard fileBoard);
//
//    // 특정 id 글 삭제
//    int delete(FileBoard fileBoard);
//
//
//    // 페이징
//    List<FileBoard> countAll();
//
//    // 게시글 출력 갯수
//    List<FileBoard> selectByPage(int from, int rows);
//
//}
