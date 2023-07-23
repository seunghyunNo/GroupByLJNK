package com.project.service;

import com.project.domain.QryResult;
import com.project.repository.WishListRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishListServiceImpl implements WishListService{
	
	private WishListRepository wishListRepository;
	
	@Autowired
	public WishListServiceImpl(SqlSession sqlSession){
		wishListRepository = sqlSession.getMapper(WishListRepository.class);
	}
	
	@Override
	public QryResult write(Long userId, String appId) {
		QryResult qryResult = QryResult.builder()
				.count(wishListRepository.addWishList(userId, appId))
				.status("OK")
				.build();
		return qryResult;
	}
	
	@Override
	public QryResult delete(Long userId, String appId) {
		QryResult qryResult = QryResult.builder()
				.count(wishListRepository.removeWishList(userId, appId))
				.status("OK")
				.build();
		return qryResult;
	}
	
	@Override
	public QryResult check(Long userId, String appId) {
		QryResult qryResult = QryResult.builder()
				.count(wishListRepository.existWishList(userId, appId))
				.status("OK")
				.build();
		return qryResult;
	}
}
