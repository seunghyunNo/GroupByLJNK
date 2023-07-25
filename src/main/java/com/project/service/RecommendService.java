package com.project.service;

import com.project.domain.QryResult;

public interface RecommendService {
    QryResult save(Long userId, Long boardId);

    QryResult delete(Long userId,Long boardId);

    int findByUserId(Long userId,Long boardId);

    int countByBoard(Long boardId);

}
