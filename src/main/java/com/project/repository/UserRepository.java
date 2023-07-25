package com.project.repository;

import com.project.domain.Authority;
import com.project.domain.User;

import java.util.List;

public interface UserRepository {

    User findByUsername(String username);

    User findByEmail(String email);

    int signup(User user);

    int userCheck(String username);

    int mailCheck(String email);

    User findById(Long userId);


}
