package com.project.controller;

import com.project.domain.EmailCheckReq;
import com.project.domain.User;
import com.project.domain.UserValidator;
import com.project.service.EmailService;
import com.project.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    public  UserController(){
        System.out.println(getClass().getName() + "() 생성");
    }

    @GetMapping("/login")
    public void login(){}

    // 로그인 실패시 request 에 담긴 attribute(errMassage, username)을 사용하기 위한 mapping
    @PostMapping("/loginError")
    public String loginError(){ return "/user/login";}

    // 접근권한
    @RequestMapping("/rejectAuth")
    public String rejectAuth(){ return  "/user/rejectAuth"; }

    // 회원가입
    @GetMapping("/signup")
    public void signup(){}

    @PostMapping("/signup")
    public String signupOk(
            @Valid User user
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttributes
    ){
        // 이미 등록된 아이디가 등록되면 errMessage 등록
        if(!result.hasFieldErrors("username") && userService.isExist(user.getUsername())){
            result.rejectValue("username", "이미 존재하는 아이디(username) 입니다.");
        }

        // 이미 등록된 이메일이 등록되면 errMessage 등록
        if(!result.hasFieldErrors("email") && userService.isEmailExist(user.getEmail())){
            result.rejectValue("username", "이미 존재하는 이메일 입니다.");
        }

        // 에러가 있다면 username 과 email 을 가지고 redirect
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("username", user.getUsername());
            redirectAttributes.addFlashAttribute("email", user.getEmail());

            List<FieldError> errorList = result.getFieldErrors();
            for(FieldError error : errorList){
                redirectAttributes.addFlashAttribute("error", error.getCode());;
                break;
            }
            return "redirect:/user/signup";
        }

        // error 없으면 회원 등록
        String page = "/user/sighupOk";
        int cnt = userService.signup(user);
        model.addAttribute("result", cnt);
        return "/user/signupOk";
    }

    @PostMapping("/signup/nameCheck")
    public @ResponseBody int nameCheck(@RequestParam("username") String username){
        System.out.println(username);
        return userService.nameCheck(username);
    }

    @PostMapping("/signup/mailCheck")
    public @ResponseBody int mailCheck(@RequestParam("email") String email){
        return userService.mailCheck(email);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {binder.setValidator(new UserValidator());}




}
