package com.project.service;

import com.project.domain.Board;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface boardService {
    // 글 작성
    int write(Board board);

    int write(Board board, Map<String, MultipartFile> files);

    @Transactional
    Board detail(Long id);

    //글 목록
    List<Board> list();

    // 페이징 리스트
    List<Board> list(Integer page, Model model);

    // 특정 id 글 읽어오기
    Board selectById(Long id);

    // id 글 수정하기
    int update(Board board);

    int update(Board board,
               Map<String, MultipartFile> files,
               Long[] defile);

    int deleteById(Long id);
}
