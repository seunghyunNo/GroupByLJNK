package com.project.repository;

import com.project.domain.BoardRecommend;

import java.util.List;

public interface BoardRecommendRepository {
    List<BoardRecommend> findByBoardId(Long id);

    int addRecommend(Long userId, Long boardId);

    int deleteRecommend(Long userId, Long boardId);
}
