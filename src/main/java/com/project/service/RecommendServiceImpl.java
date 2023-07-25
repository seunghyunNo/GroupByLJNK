package com.project.service;

import com.project.domain.Recommend;
import com.project.repository.RecommendRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendServiceImpl implements RecommendService{

    private RecommendRepository recommendRepository;

    public RecommendServiceImpl(SqlSession sqlSession){
        recommendRepository = sqlSession.getMapper(RecommendRepository.class);
    }
    @Override
    public List<Recommend> findByBoardId(Long id) {
        return recommendRepository.findByBoardId(id);
    }
}
