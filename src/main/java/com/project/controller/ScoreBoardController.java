package com.project.controller;

import com.project.config.PrincipalDetails;
import com.project.domain.QryAvgScore;
import com.project.domain.QryResult;
import com.project.domain.QryScoreList;
import com.project.domain.User;
import com.project.service.ScoreBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
			@RequestParam("app_id") String appId
	){
		PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		Long id = user.getId();
		return scoreBoardService.find(appId, id);
	}
	
	@PostMapping("/write")
	public QryResult write(
		@RequestParam("app_id") String appId,
		@RequestParam("score") Long score,
		@RequestParam("content") String content
	){
		PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		Long id = user.getId();
		
		return scoreBoardService.write(appId, id, score, content);
	}
	
	@PostMapping("/delete")
	public QryResult delete(
			@RequestParam("app_id") String appId,
			@RequestParam("user_id") Long userId
	){
		return scoreBoardService.delete(appId, userId);
	}
	
	@PostMapping("/avg")
	public QryAvgScore avgScore(@RequestParam("app_id") String appId){
		return scoreBoardService.avgScore(appId);
	}
}
