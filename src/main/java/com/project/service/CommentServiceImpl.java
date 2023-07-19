package com.project.service;

import com.project.domain.Comment;
import com.project.domain.CommentList;
import com.project.domain.CommentResult;
import com.project.repository.CommentRepository;
import com.project.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(SqlSession sqlSession){
        commentRepository = sqlSession.getMapper(CommentRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);

    }

    @Override
    public CommentList list(Long id) {
        CommentList list = new CommentList();

        List<Comment> comments = commentRepository.findByBoard(id);

        list.setCount(comments.size());
        list.setList(comments);
        list.setStatus("ok");

        return list;
    }

    @Override
    public CommentResult write(Long boardId, Long userId, String content) {
        return null;
    }

    @Override
    public CommentResult delete(Long id) {
        return null;
    }
}
