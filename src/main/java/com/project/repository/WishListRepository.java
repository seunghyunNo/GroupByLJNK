package com.project.repository;

public interface WishListRepository {
	int addWishList(Long userId, Long appId);
	
	int removeWishList(Long userId, Long appId);
	
	int existWishList(Long userId, Long appId);
}
