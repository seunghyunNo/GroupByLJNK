package com.project.controller;

import com.project.domain.Comment;
import com.project.domain.CommentList;
import com.project.domain.CommentResult;
import com.project.domain.User;
import com.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //data response 함
@RequestMapping("/comment")
import org.springframework.web.bind.annotation.GetMapping;

public class BoardCommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/test")
    public CommentResult test(){
        return CommentResult.builder()
                .count(2)
                .status("o")
                .build();
    }

    @GetMapping("/test1")
    public CommentList test1(){
        CommentList list = new CommentList();
        list.setCount(1);
        list.setStatus("o");


        return list;
    }

    // 여기서부터 수정
//    @GetMapping("/list")
//    public CommentList list(Long id){
////        return
//    }

//    @GetMapping("/write")
//    public
}
