package com.project.repository;

import com.project.domain.Score;

import java.util.List;

public interface ScoreBoardRepository {
	List<Score> findByApp(String appId);
	
	Score findByUser(String appId, Long userId);
	
	int saveScore(Score score);
	
	int deleteByIds(String appId, Long userId);
	
	Float avgScore(String appId);
	
}
