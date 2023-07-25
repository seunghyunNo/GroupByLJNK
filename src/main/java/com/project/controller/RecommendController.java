package com.project.controller;


import com.project.domain.Recommend;
import com.project.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @GetMapping("/board")
    public List<Recommend> list(@RequestParam("board_id") Long id){
        return recommendService.findByBoardId(id);
    }
}
