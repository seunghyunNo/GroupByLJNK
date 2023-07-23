package com.project.service;

import com.project.config.PrincipalDetails;
import com.project.domain.Authority;
import com.project.domain.User;
import com.project.repository.AuthorityRepository;
import com.project.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(SqlSession session){
        userRepository = session.getMapper(UserRepository.class);
        authorityRepository = session.getMapper(AuthorityRepository.class);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) { return userRepository.findByEmail(email); }

    @Override
    public boolean isExist(String username) {

        // user 에 찾아온 user 정보 담기
        User user = findByUsername(username);

        // user 가 비어있지않으면 true 비어있으면 false 리턴
        return (user != null)? true : false;
    }

    @Override
    public boolean isEmailExist(String email) {

        // user 에 email 로 찾아온 user 정보 담기
        User user = findByEmail(email);

        // user 가 비어있지않으면 true 비어있으면 false 리턴
        return (user != null)? true : false;
    }

    @Override
    public int signup(User user) {
        // password 암호화 하여 저장
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.signup(user);

        // 회원가입시 기본 MEMBER 권한 등록
        // DB에서 ROLE_MEMBER 의 name 과 id 값을 authority 에 저장
        Authority authority = authorityRepository.findByName("ROLE_MEMBER");
        // user 에서 id(PK) 값 저장
        Long user_id = user.getId();
        // authority 에서 ROLE_MEMBER 의 id 값 저장
        Long authority_id = authority.getId();
        // t_user_authorities DB에 유저 id 와 ROLE_MEMBER id 등록
        authorityRepository.addAuthority(user_id, authority_id);

        // 등록 완료 후 1 리턴
        return 1;
    }

    @Override
    public int nameCheck(String username) {
        return userRepository.userCheck(username);
    }

    @Override
    public int mailCheck(String email) {
        return userRepository.mailCheck(email);
    }

    @Override
    public String findUsername(String email) {
        return userRepository.findUsername(email);
    }

    @Override
    public int findPw(String username, String email) {
        return userRepository.findPw(username, email);
    }

    @Override
    public int updatePw(User user) {
        // password 암호화 하여 저장
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.updatePw(user);
    }

    @Override
    public boolean pwCheck(User user) {
        String encodePassword = userRepository.getEncodePassword(user);
        return passwordEncoder.matches(user.getPassword(), encodePassword);
    }

    @Override
    public int delete(Long id) {
       return userRepository.deleteUser(id);
    }

    @Override
    public List<Authority> findAuthorityById(Long id) {
        // id 값으로 해당 유저의 Authority 를 List 에 담아 return
        return authorityRepository.findAuthorityById(id);
    }

    @Override
    public List<String> findWishListById(Long id) {
        return userRepository.findWishListById(id);
    }
}
