package com.project.service;

import com.project.domain.QryCommentList;
import com.project.domain.QryResult;

public interface CommentService {
    QryCommentList list(Long id);

    // 글에 사용자가 댓글 작성
    QryResult write(Long boardId, Long userId, String content);

    QryResult delete(Long id);
}
