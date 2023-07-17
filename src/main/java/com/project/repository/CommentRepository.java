package com.project.repository;

import com.project.domain.Comment;

public interface CommentRepository {
    int write(Comment comment);

    int deleteById(Long id);
}