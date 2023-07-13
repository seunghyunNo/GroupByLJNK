package com.project.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    private final String DEFAULT_FAILURE_FORWARD_URL = "/user/loginError";
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("로그인 실패 : CustomLoginFailureHandler() 호출");

        String errMessage = null;

        if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            errMessage = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해 주십시오.";
        }
        //< account is disabled
        else if(exception instanceof DisabledException) {
            errMessage = "계정이 비활성화 되었습니다. 관리자에게 문의하세요.";
        }
        //< expired the credential
        else if(exception instanceof CredentialsExpiredException) {
            errMessage = "비밀번호 유효기간이 만료 되었습니다. 관리자에게 문의하세요.";
        }
        else {
            errMessage = "알수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.";
        }

        // 에러 메세지와 username 을 request 에 담아 redirect 혹은 forward 된 페이지에 사용
        request.setAttribute("errMessage", errMessage);
        request.setAttribute("username", request.getParameter("username"));

        // DEFAULT_FAILURE_FORWARD_URL 로 redirect 하거나 forward 시킨다.
        request.getRequestDispatcher(DEFAULT_FAILURE_FORWARD_URL).forward(request, response);


    }
}
