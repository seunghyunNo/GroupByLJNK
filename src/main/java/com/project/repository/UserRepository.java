package com.project.repository;

import com.project.domain.Authority;
import com.project.domain.User;

import java.util.List;

public interface UserRepository {

    User findByUsername(String username);

    int join(User user);

    List<Authority> findAuthorityById(Long id);
}
