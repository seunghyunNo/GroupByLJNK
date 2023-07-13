package com.project.service;

import com.project.domain.Authority;
import com.project.domain.User;
import com.project.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(SqlSession session){
        userRepository = session.getMapper(UserRepository.class);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isExist(String username) {

        // user 에 찾아온 user 정보 담기
        User user = findByUsername(username);

        // user 가 비어있지않으면 true 비어있으면 false 리턴
        return (user != null)? true : false;
    }

    @Override
    public int join(User user) {
        return userRepository.join(user);
    }

    @Override
    public List<Authority> findAuthorityById(Long id) {
        return null;
    }
}
