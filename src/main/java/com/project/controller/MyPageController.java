package com.project.controller;

import com.project.config.PrincipalDetails;
import com.project.domain.User;
import com.project.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class MyPageController {


    @Autowired
    private MyPageService myPageService;

    @GetMapping("/mypage/myBoard")
    public void myBoard(Integer page, Model model){

        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Long id = user.getId();
        String username = user.getUsername();

        model.addAttribute("id", id);
        model.addAttribute("username",username);

        model.addAttribute("list",myPageService.boardList(model,page,id));
    }

    @GetMapping("/mypage/myFileBoard")
    public String myFileBoard(Integer page, Model model){

        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Long id = user.getId();
        String username = user.getUsername();

        model.addAttribute("id", id);
        model.addAttribute("username",username);

        model.addAttribute("list",myPageService.fileList(model,page,id));

        return "/user/mypage/myFileboard";
    }


}
