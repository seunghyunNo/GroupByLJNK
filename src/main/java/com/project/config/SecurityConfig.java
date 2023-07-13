package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Security 비활성화
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().anyRequest();
    }

    // PasswordEncoder Bean 등록
    @Bean
    public PasswordEncoder encoder(){
        System.out.println("PasswordEncoder() 생성");
        return new BCryptPasswordEncoder();
    }

    // Security 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return  http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // write, update url로 오는 요청은 MEMBER 혹은 ADMIN 권한이 필요
                        .requestMatchers("/board/write/**", "/board/update/**", "/fileboard/write/**", "/fileboard/update/**", "/user/update").hasAnyRole("MEMBER", "ADMIN")
                        // 그외 나머지 요청은 permit
                        .anyRequest().permitAll()
                )

                .formLogin(form -> form
                        // 로그인 필요상황 발생시 "/user/login" request 발생
                        .loginPage("/user/login")
                        // "user/login" url 로 POST request 가 들어오면 시큐리티가 로그인 진행
                        .loginProcessingUrl("/user/login")
                        // 로그인 성공시 "/"로 이동
                        .defaultSuccessUrl("/")
                        .successHandler(new CustomLoginSuccessHandler("/home"))
                        .failureHandler(new CustomLoginFailureHandler())
                )

                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(false)
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                )

                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedHandler(new CustomAccessDenieHandler())
                )

                .build();
    }


}
