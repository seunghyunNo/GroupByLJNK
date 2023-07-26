    package com.project.config;

    import com.project.domain.User;
    import com.project.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;

    @Service
    public class PrincipalDetailsService implements UserDetailsService {

        @Autowired
        private UserService userService;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            // login 에서 넘어온 username 으로 DB 조회 후 user 에 대입
            User user = userService.findByUsername(username);

            // user 가 DB에 존재한다면 UserDetails 생성해서 리턴
            if(user != null){
                PrincipalDetails userDetails = new PrincipalDetails(user);
                userDetails.setUserService(userService);
                return userDetails;
            }

            // user 가 DB에 없다면 예외문 throw
            throw new UsernameNotFoundException(username);
        }


    }
