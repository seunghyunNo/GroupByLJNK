package com.project.controller;

import com.project.domain.QryCommentList;
import com.project.domain.QryResult;
import com.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController //data response 함
@RequestMapping("/comment")
public class BoardCommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/test")
    public QryResult test(){
        return QryResult.builder()
                .count(2)
                .status("o")
                .build();
    }

    @GetMapping("/test1")
    public QryCommentList test1(){
        QryCommentList list = new QryCommentList();
        list.setCount(1);
        list.setStatus("o");

        return list;
    }


    @GetMapping("/list")
    public QryCommentList list(Long id){
        return commentService.list(id);
    }

    @PostMapping("/write")
    public QryResult write(
            @RequestParam("board_id") Long boardId,
            @RequestParam("user_name")String username,
            String content){
        return commentService.write(boardId,username,content);
    }

    @PostMapping("/delete")
    public QryResult delete(Long id){
        return commentService.delete(id);
    }
}
