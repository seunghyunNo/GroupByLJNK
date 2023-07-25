package com.project.controller;


import com.project.domain.BoardRecommend;

import com.project.service.BoardRecommendService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommend")
public class BoardRecommendController {

    @Autowired
    private BoardRecommendService recommendService;

    @GetMapping("/board")
    public List<BoardRecommend> list(@RequestParam("board_id") Long id){
        return recommendService.findByBoardId(id);
    }

    @PostMapping("/addRecommend")
    public int addRecommend(@RequestParam("user_id") Long user_Id,@RequestParam("board_id") Long board_Id){
        System.out.println(user_Id);
        return recommendService.addRecommend(user_Id, board_Id);
    }

    @PostMapping("/deleteRecommend")
    public int deleteRecommend(@RequestParam("user_id") Long user_Id,@RequestParam("board_id") Long board_Id){
        return recommendService.deleteRecommend(user_Id, board_Id);
    }
}