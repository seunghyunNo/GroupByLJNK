package com.project.controller;

import com.project.domain.QryResult;
import com.project.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @GetMapping("/count")
    public int count(Long boardId)
    {
        return recommendService.countByBoard(boardId);
    }

    @PostMapping("/check")
    public int check(@RequestParam Long userId,@RequestParam Long boardId)
    {
        System.out.println(userId+":"+boardId);
        int result =recommendService.findByUserId(userId,boardId);
        System.out.println(result);
        if(result > 0)
        {
           recommendService.delete(userId,boardId);
        }
        else {
            recommendService.save(userId,boardId);
        }
        return result;
    }
}
