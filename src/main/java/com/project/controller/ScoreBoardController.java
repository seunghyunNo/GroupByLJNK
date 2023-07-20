package com.project.controller;

import com.project.domain.QryResult;
import com.project.domain.QryScoreList;
import com.project.service.ScoreBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/score")
public class ScoreBoardController {
	@Autowired
	private ScoreBoardService scoreBoardService;
	
	@PostMapping("/list/{appId}")
	public QryScoreList list(@PathVariable String appId){
		return scoreBoardService.list(appId);
	}
	
	@PostMapping("/find")
	public QryScoreList find(
			@RequestParam("app_id") String appId,
			@RequestParam("user_id") Long userId
	){
		return scoreBoardService.find(appId, userId);
	}
	
	@PostMapping("/write")
	public QryResult write(
		@RequestParam("app_id") String appId,
		@RequestParam("user_id") Long userId,
		@RequestParam("score") Long score,
		@RequestParam("content") String content
	){
		return scoreBoardService.write(appId, userId, score, content);
	}
	
	@PostMapping("/delete")
	public QryResult delete(
			String appId,
			Long userId
	){
		return scoreBoardService.delete(appId, userId);
	}
}
