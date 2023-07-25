package com.project.service;


import com.project.domain.BoardRecommend;

import java.util.List;

public interface BoardRecommendService {

    List<BoardRecommend> findByBoardId(Long id);

    int addRecommend(Long userId, Long boardId);

    int deleteRecommend(Long userId, Long boardId);
}
