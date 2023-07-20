package com.project.service;

import org.springframework.http.ResponseEntity;

public interface ApiService {
	ResponseEntity<Object> getData(String appId);
}
