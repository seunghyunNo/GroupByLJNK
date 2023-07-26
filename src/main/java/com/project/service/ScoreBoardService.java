package com.project.service;

import com.project.domain.QryAvgScore;
import com.project.domain.QryResult;
import com.project.domain.QryScoreList;

public interface ScoreBoardService {
	QryScoreList list(String appId);
	
	QryScoreList find(String appId, Long userId);
	
	QryResult write(String appId, Long userId, Long Score, String content);
	
	QryResult delete(String appId, Long userId);
	
	QryAvgScore avgScore(String appId);
	
}
