package com.project.service;

import com.project.domain.QryResult;

public interface RecommendService {
    Integer save(Long userId, Long boardId);

    Integer delete(Long userId,Long boardId);

    int findByUserId(Long userId,Long boardId);

    int countByBoard(Long boardId);

}
