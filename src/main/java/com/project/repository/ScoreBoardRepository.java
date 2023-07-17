package com.project.repository;

import java.util.List;

public interface ScoreBoardRepository {
	List<?> findByApp(String appId);
	
}
