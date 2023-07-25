package com.project.service;

import com.project.domain.Comment;
import com.project.domain.QryCommentList;
import com.project.domain.QryResult;
import com.project.domain.User;
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
    public QryCommentList list(Long id) {
        QryCommentList list = new QryCommentList();

        List<Comment> comments = commentRepository.findByBoard(id);

        list.setCount(comments.size());
        list.setList(comments);
        list.setStatus("ok");

        return list;
    }

    @Override
    public QryResult write(Long boardId, Long userId, String content) {
        User user = userRepository.findById(userId);
        Comment comment = Comment.builder()
                .user(user)
                .content(content)
                .board_id(boardId)
                .build();
        commentRepository.write(comment);
        QryResult result = QryResult.builder()
                .count(1)
                .status("ok")
                .build();
        return result;
    }

    @Override
    public QryResult delete(Long id) {

        int count = commentRepository.deleteById(id);
        String status="fail";
        if(count==1) status = "ok";

        QryResult result = QryResult.builder()
                .count(count)
                .status(status)
                .build();

        return result;
    }
}
