package com.project.repository;

import com.project.config.PrincipalDetails;
import com.project.domain.Authority;
import com.project.domain.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserRepository {

    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    int signup(User user);

    int userCheck(String username);

    int mailCheck(String email);

    String findUsername(String email);

    int findPw(String username, String email);

    int updatePw(User user);

    String getEncodePassword(User user);

    int deleteUser(Long id);

    List<String> findWishListById(Long id);
}
