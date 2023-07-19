package com.project.service;

import com.project.domain.CommentList;
import com.project.domain.CommentResult;

public interface CommentService {
    CommentList list(Long id);

    // 글에 사용자가 댓글 작성
    CommentResult write(Long boardId, Long userId, String content);

    CommentResult delete(Long id);
}
