package com.project.service;

import com.project.domain.QryResult;

public interface WishListService {
	QryResult write(Long userId, String appId);
	QryResult delete(Long userId, String appId);
	
	QryResult check(Long userId, String appId);
}
