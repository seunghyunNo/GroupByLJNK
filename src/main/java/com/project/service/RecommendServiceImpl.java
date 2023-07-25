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
    public QryResult save(Long userId, Long boardId) {

        recommendRepository.insertRecommend(userId,boardId);

        QryResult result =QryResult.builder()
                .count(1)
                .status("OK")
                .build();

        return result;
    }
    @Override
    public QryResult delete(Long userId, Long boardId) {
        int count = recommendRepository.deleteRecommend(userId,boardId);
        String status ="FAIL";
        if(count == 1)
        {
            status = "OK";
        }

        QryResult result = QryResult.builder()
                .count(count)
                .status(status)
                .build();

        return result;
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
