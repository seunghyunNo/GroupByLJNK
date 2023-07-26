package com.project.controller;

import com.project.domain.QryResult;
import com.project.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
	
	@Autowired
	private WishListService wishListService;
	
	@PostMapping("/check")
	public QryResult check(
			@RequestParam("user_id") Long userId,
			@RequestParam("app_id") String appId
	){
		return wishListService.check(userId, appId);
	}
	
	@PostMapping("/write")
	public QryResult write(
			@RequestParam("user_id") Long userId,
			@RequestParam("app_id") String appId
	){
		return wishListService.write(userId, appId);
	}
	
	@PostMapping("/delete")
	public  QryResult delete(
			@RequestParam("user_id") Long userId,
			@RequestParam("app_id") String appId
	){
		return wishListService.delete(userId, appId);
	}
	
}
