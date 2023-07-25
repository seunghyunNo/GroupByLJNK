package com.project.service;


import com.project.domain.Recommend;

import java.util.List;

public interface RecommendService {

    List<Recommend> findByBoardId(Long id);
}
