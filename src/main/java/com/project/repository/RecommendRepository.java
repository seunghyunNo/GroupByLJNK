package com.project.repository;

import com.project.domain.Recommend;

import java.util.List;

public interface RecommendRepository {
    List<Recommend> findByBoardId(Long id);
}
