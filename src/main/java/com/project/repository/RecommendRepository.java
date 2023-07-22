package com.project.repository;



public interface RecommendRepository {
    public int updateRecommend(Long boardId,Long userId);

    public int deleteRecommend(Long boardId,Long userId);

    public int checkRecommend(Long boardId,Long userId);

    public Long getRecNo(Long boardId);
}
