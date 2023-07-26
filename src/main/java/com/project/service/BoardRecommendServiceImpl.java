package com.project.service;

import com.project.domain.BoardRecommend;
import com.project.repository.BoardRecommendRepository;
import com.project.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardRecommendServiceImpl implements BoardRecommendService{

    private BoardRecommendRepository boardRecommendRepository;


    public BoardRecommendServiceImpl(SqlSession sqlSession){
        boardRecommendRepository = sqlSession.getMapper(BoardRecommendRepository.class);

    }
    @Override
    public List<BoardRecommend> findByBoardId(Long id) {
        return boardRecommendRepository.findByBoardId(id);
    }

    @Override
    public int addRecommend(Long userId, Long boardId) {
        return boardRecommendRepository.addRecommend(userId, boardId);
    }

    @Override
    public int deleteRecommend(Long userId, Long boardId) {
        return boardRecommendRepository.deleteRecommend(userId, boardId);
    }
}