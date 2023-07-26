package com.project.controller;

import com.project.domain.QryCommentList;
import com.project.domain.QryResult;
import com.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController //data response 함
@RequestMapping("/comment")
public class BoardCommentController {

    @Autowired
    private CommentService commentService;



    @GetMapping("/list")
    public QryCommentList list(Long id){

        return commentService.list(id);
    }

    @PostMapping("/write")
    public QryResult write(
            @RequestParam("board_id") Long boardId,
            @RequestParam("user_id") Long userId,
            String content){
        return commentService.write(boardId,userId,content);
    }

    @PostMapping("/delete")
    public QryResult delete(Long id){
        return commentService.delete(id);
    }
}
