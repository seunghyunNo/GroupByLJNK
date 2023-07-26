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

    @PostMapping("/count")
    public QryResult count(Long boardId)
    {
        QryResult result = QryResult.builder()
                .count(recommendService.countByBoard(boardId))
                .status("OK")
                .build();

        return result;
    }

    @PostMapping("/check")
    public QryResult check(@RequestParam Long userId,@RequestParam Long boardId)
    {
        QryResult result = QryResult.builder()
                .count(recommendService.findByUserId(userId,boardId))
                .status("OK")
                .build();
        return result;
    }

    @PostMapping("/write")
    public QryResult write(@RequestParam Long userId,@RequestParam Long boardId)
    {
        QryResult result = QryResult.builder()
                .count(recommendService.save(userId,boardId))
                .status("OK")
                .build();

        return result;
    }

    @PostMapping("/delete")
    public QryResult delete(@RequestParam Long userId,@RequestParam Long boardId)
    {
        QryResult result = QryResult.builder()
                .count(recommendService.delete(userId,boardId))
                .status("OK")
                .build();

        return result;
    }
}
