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


    @GetMapping("/list/{appId}/{id}")
    public QryCommentList list(Long id, Model model,@PathVariable String appId){
        model.addAttribute("appId",appId);
        return commentService.list(id);
    }

    @PostMapping("/write/{appId}/{id}")
    public QryResult write(
            @PathVariable String appId,
            Model model,
            @RequestParam("board_id") Long boardId,
            @RequestParam("user_name")String username,
            String content){
        model.addAttribute("appId",appId);
        return commentService.write(boardId,username,content);
    }

    @PostMapping("/delete")
    public QryResult delete(Long id){
        return commentService.delete(id);
    }
}
