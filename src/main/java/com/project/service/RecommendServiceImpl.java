package com.project.service;

import com.project.domain.QryResult;
import com.project.domain.Recommend;
import com.project.domain.User;
import com.project.repository.RecommendRepository;
import com.project.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendServiceImpl implements RecommendService{

    private RecommendRepository recommendRepository;
    private UserRepository userRepository;

    @Autowired
    public RecommendServiceImpl(SqlSession sqlSession) {
        recommendRepository = sqlSession.getMapper(RecommendRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
    }

    @Override
    public Integer save(Long userId, Long boardId) {

        int result = recommendRepository.insertRecommend(userId,boardId);

        return result;
    }
    @Override
    public Integer delete(Long userId, Long boardId) {
        int count = recommendRepository.deleteRecommend(userId,boardId);

        return count;
    }

    @Override
    public int findByUserId(Long userId, Long boardId) {

        int result = recommendRepository.selectByUserId(userId,boardId);

        return result;
    }

    @Override
    public int countByBoard(Long boardId) {
        return recommendRepository.countByBoardId(boardId);
    }
}
