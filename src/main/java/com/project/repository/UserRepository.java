package com.project.repository;

import com.project.domain.Authority;
import com.project.domain.User;

import java.util.List;

public interface UserRepository {

    User findByUsername(String username);

    int signup(User user);


}
