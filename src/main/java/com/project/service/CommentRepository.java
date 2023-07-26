package com.project.service;

import com.project.domain.Comment;

import java.util.List;

public interface CommentRepository {

    //특정 글의 댓글 목록
    List<Comment> findByBoard(Long board_id);

    int write(Comment comment);

    int deleteById(Long id);
}
