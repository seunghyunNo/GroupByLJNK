package com.project.repository;

public interface WishListRepository {
	int addWishList(Long userId, String appId);
	
	int removeWishList(Long userId, String appId);
	
	int existWishList(Long userId, String appId);
}
