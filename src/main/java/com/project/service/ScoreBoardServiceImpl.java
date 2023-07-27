package com.project.service;

import com.project.domain.QryAvgScore;
import com.project.domain.QryResult;
import com.project.domain.QryScoreList;
import com.project.domain.Score;
import com.project.repository.ScoreBoardRepository;
import com.project.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScoreBoardServiceImpl implements ScoreBoardService{
	
	private ScoreBoardRepository scoreBoardRepository;
	
	@Autowired
	public ScoreBoardServiceImpl(SqlSession sqlSession){
		scoreBoardRepository = sqlSession.getMapper(ScoreBoardRepository.class);
	}
	
	@Override
	public QryScoreList list(String appId) {
		
		List<Score> list = scoreBoardRepository.findByApp(appId);
		QryScoreList qryScoreList = new QryScoreList();
		qryScoreList.setScores(list);
		qryScoreList.setCount(list.size());
		qryScoreList.setStatus("OK");
		
		return qryScoreList;
	}
	
	@Override
	public QryScoreList find(String appId, Long userId) {
		QryScoreList qryScoreList = new QryScoreList();
		qryScoreList.setStatus("FAIL");
		
		Score score = scoreBoardRepository.findByUser(appId, userId);
		if (score != null) {
			qryScoreList.setStatus("OK");
			qryScoreList.setCount(1);
			qryScoreList.setScores(List.of(score));
		}
		
		return qryScoreList;
	}
	
	@Override
	public QryResult write(String appId, Long userId, Long score, String content) {
		Score scoreResult = Score.builder()
				.appId(appId)
				.content(content)
				.score(score)
				.userId(userId)
				.build();
		int count = scoreBoardRepository.saveScore(scoreResult);
		QryResult qryResult = QryResult.builder()
				.count(count)
				.status("OK")
				.build();
		
		return qryResult;
	}
	
	@Override
	public QryResult delete(String appId, Long userId) {
		
		int count = scoreBoardRepository.deleteByIds(appId, userId);
		String status = "FAIL";
		if (count == 1) { status = "OK"; }
		
		QryResult qryResult = QryResult.builder()
				.status(status)
				.count(count)
				.build();
		return qryResult;
	}
	
	@Override
	public QryAvgScore avgScore(String appId) {
		Float avg = scoreBoardRepository.avgScore(appId);
		String status;
		int count;
		if (avg==null){
			status = "FAIL";
			count = 0;
		} else {
			status = "OK";
			count = 1;
		}
		
		QryAvgScore qryAvgScore = new QryAvgScore();
		qryAvgScore.setAvg(avg);
		qryAvgScore.setStatus(status);
		qryAvgScore.setCount(count);
		
		return qryAvgScore;
	}
}
