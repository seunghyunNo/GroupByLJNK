package com.project.service;

import com.project.config.PrincipalDetails;
import com.project.domain.Authority;
import com.project.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    // 아이디(username)로 User 정보 가져오기
    User findByUsername(String username);

    // email로 User 정보 가져오기
    User findByEmail(String email);

    // 아이디(username)로 회원이 존재하는지 여부 확인
    boolean isExist(String username);

    // 아이디(username)로 회원이 존재하는지 여부 확인
    boolean isEmailExist(String email);

    // 신규 회원 가입
    int signup(User user);

    int nameCheck(String username);

    int mailCheck(String email);

    String findUsername(String email);

    int findPw(String username, String email);

    int updatePw(User user);

    boolean pwCheck(User user);

    int delete(Long id);

    // 로그인 유저의 Id(pk)로 해당 유저의 authority(들) 가져오기
    List<Authority> findAuthorityById(Long id);

    List<String> findWishListById(Long id);

}
