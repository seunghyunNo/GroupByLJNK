package com.project.service;

import com.project.domain.Authority;
import com.project.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    // 아이디(username)로 User 정보 가져오기
    User findByUsername(String username);

    // 아이디(username)로 회원이 존재하는지 여부 확인
    boolean isExist(String username);

    // 신규 회원 가입
    int join(User user);

    // id(pk)로 authority(들) 가져오기
    List<Authority> findAuthorityById(Long id);
}
