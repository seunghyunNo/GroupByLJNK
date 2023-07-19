package com.project.controller;

import com.project.domain.QryCommentList;
import com.project.domain.QryResult;
import com.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // 여기서부터 수정
//    @GetMapping("/list")
//    public CommentList list(Long id){
////        return
//    }

//    @GetMapping("/write")
//    public
}
