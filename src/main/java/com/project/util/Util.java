package com.project.util;

import com.project.config.PrincipalDetails;
import com.project.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Util {

    public static HttpServletRequest getRequest()
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getRequest();
    }

    public static HttpSession getSession()

    {
        return getRequest().getSession();
    }

    public static User getLoggedUser()
    {
        PrincipalDetails userDetail = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetail.getUser();
        return user;
    }
}
