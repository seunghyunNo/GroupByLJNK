package com.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PrincipalDetailsService principalDetailsService;

    // Security 비활성화
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return web -> web.ignoring().anyRequest();
//    }

    // PasswordEncoder Bean 등록
//    @Bean
//    public PasswordEncoder encoder(){
//        System.out.println("PasswordEncoder() 생성");
//        return new BCryptPasswordEncoder();
//    }

    // Security 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return  http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // write, update url로 오는 요청은 MEMBER 혹은 ADMIN 권한이 필요
                        .requestMatchers("/board/write/**", "/board/update/**", "/fileboard/write/**", "/fileboard/update/**", "/user/pwCheck", "/user/update", "/user/myPage", "user/mypage/myBoard", "user/mypage/myFileBoard", "/score/write", "/score/delete", "/board/detail/**").hasAnyRole("MEMBER", "ADMIN")
                        // 그외 나머지 요청은 permit
                        .requestMatchers("/user/login", "/user/signup", "user/findUsername", "user/findPw").anonymous()
                        .anyRequest().permitAll()
                )

                .formLogin(form -> form
                        // 로그인 필요상황 발생시 "/user/login" request 발생
                        .loginPage("/user/login")
                        // "user/login" url 로 POST request 가 들어오면 시큐리티가 로그인 진행
                        // PrincipalDetailsService 에 loadUserByUsername() 이 실행되어 인증 진행 후 UserDetails 리턴
                        // 후 PrincipalDetails 에서 UserDetails 안의 유저정보를 꺼내기위해 사용
                        .loginProcessingUrl("/user/login")
                        // 로그인 성공시 "/"로 이동
                        .defaultSuccessUrl("/")
                        // 로그인 성공시 실행할 코드
                        .successHandler(new CustomLoginSuccessHandler("/home"))
                        // 로그인 실패시 실행할 코드
                        .failureHandler(new CustomLoginFailureHandler())
                )

                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        // 로그아웃 request url
                        .logoutUrl("/user/logout")
                        // 로그아웃 후 실행할 코드
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                        .invalidateHttpSession(true) // 세션 삭제
                        .deleteCookies("JSESSIONID") // 쿠키삭제
                )

                // remember-me 기능 활성화
                .rememberMe(rememberMe -> rememberMe
                        // token 생성용 key 값
                        .key("key")
                        // rememberMe 파라미터
                        .rememberMeParameter("rememberMe")
                        // remember-me 토큰 유효 시간 -> 7일
                        .tokenValiditySeconds(60*60*24*7)
                        // 사용자 서비스 설정 (사용자 정보 로드)
                        .userDetailsService(principalDetailsService)
                )

                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        // 접근권한
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )

                .build();
    }


}
