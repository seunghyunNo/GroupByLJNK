package com.project.repository;


import com.project.domain.Recommend;

public interface RecommendRepository {
    int insertRecommend(Long userId,Long boardId);

    int deleteRecommend(Long userId,Long boardId);

    int selectByUserId(Long userId,Long boardId);

    int countByBoardId(Long boardId);
}
