package com.project.controller;

import com.project.config.PrincipalDetails;
import com.project.domain.Board;
import com.project.domain.EmailCheckReq;
import com.project.domain.User;
import com.project.domain.UserValidator;
import com.project.service.EmailService;
import com.project.service.MyPageService;
import com.project.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MyPageService myPageService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public void login(){}

    // 로그인 실패시 request 에 담긴 attribute(errMassage, username)을 사용하기 위한 mapping
    @PostMapping("/loginError")
    public String loginError(){ return "/user/login";}

    // 접근권한
    @RequestMapping("/rejectAuth")
    public String rejectAuth(){ return  "/user/rejectAuth"; }

    // 회원가입
    @PreAuthorize("isAnonymous()")
    @GetMapping("/signup")
    public void signup(){}

    @PostMapping("/signup")
    public String signupOk(
            @Valid User user
            ,@RequestParam("authCodeInput") String authCodeInput
            ,@RequestParam("code") String code
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttributes
    ){
        // 이미 등록된 아이디가 등록되면 errMessage 등록
        if(!result.hasFieldErrors("username") && userService.isExist(user.getUsername())){
            result.rejectValue("username", "이미 존재하는 아이디(username) 입니다.");
        } else if( user.getUsername().length() < 5 ){
            result.rejectValue("username", "아이디(username)는 5글자 이상이어야 합니다.");
        }

        // 이미 등록된 이메일이 등록되면 errMessage 등록
        if(!result.hasFieldErrors("email") && userService.isEmailExist(user.getEmail())){
            result.rejectValue("email", "이미 존재하는 이메일 입니다.");
        }

        // 에러가 있다면 username 과 email 을 가지고 redirect
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("username", user.getUsername());
            redirectAttributes.addFlashAttribute("email", user.getEmail());
            redirectAttributes.addFlashAttribute("authCodeInput", authCodeInput);
            redirectAttributes.addFlashAttribute("code", code);

            List<FieldError> errorList = result.getFieldErrors();
            for(FieldError error : errorList){
                redirectAttributes.addFlashAttribute("error", error.getCode());;
                break;
            }
            return "redirect:/user/signup";
        }

        // error 없으면 회원 등록
        String page = "/user/signupOk";
        int cnt = userService.signup(user);
        model.addAttribute("result", cnt);
        return page;
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

    @PreAuthorize("isAnonymous()")
    @GetMapping("/findUsername")
    public void findUsername(){}

    @PostMapping("/findUsername")
    public @ResponseBody String findUsernameOk(@RequestParam("email") String email){
        return userService.findUsername(email);
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/findPw")
    public void findPw(){}

    @PostMapping("/findPw")
    public @ResponseBody int findPwOk(@RequestParam("username") String username, @RequestParam("email") String email){
        return userService.findPw(username, email);
    }

    @PostMapping("/updatePw")
    public String updatePw(
            @Valid User user
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttributes){
        // 에러가 있다면 username 과 email 을 가지고 redirect
        if(result.hasErrors()){

            List<FieldError> errorList = result.getFieldErrors();
            for(FieldError error : errorList){
                redirectAttributes.addFlashAttribute("error", error.getCode());;
                break;
            }
            return "redirect:/user/findPw";
        }

        // error 없으면 비밀번호 수정
        String page = "/user/updatePw";
        int cnt = userService.updatePw(user);
        model.addAttribute("result", cnt);
        return page;
    }

    @GetMapping("/pwCheck")
    public void pwCheck(Principal principal, Model model){
        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());;
    }

    @PostMapping("/pwCheck")
    public String pwCheckOk(User user, HttpSession session, RedirectAttributes redirectAttributes){
        if(userService.pwCheck(user)) {
            // password 가 확인이 되면 "pwCheckComplete"을 세션에 저장
            session.setAttribute("pwCheckComplete", true);
            return "redirect:/user/update";
        } else {
            redirectAttributes.addFlashAttribute("error", "비밀번호가 틀렸습니다.");
            return "redirect:/user/pwCheck";
        }
    }

    @GetMapping("/update")
    public String update(HttpSession session, Model model){
        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());;

        // 세션에서 "pwCheckComplete" 속성을 확인하여 비밀번호 확인이 완료되지 않았으면 비밀번호 확인 페이지로 리다이렉트
        Boolean pwCheckComplete = (Boolean) session.getAttribute("pwCheckComplete");
        if (pwCheckComplete == null || !pwCheckComplete) {
            return "redirect:/user/pwCheck"; // 비밀번호 확인 페이지로 리다이렉트
        }

        // 더 이상 필요하지 않은 상태 정보를 세션에서 삭제
        session.removeAttribute("pwCheckComplete");
        return "/user/update";
    }

    @PostMapping("/update")
    public String updateOk(
            @Valid User user
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttributes
    ){
        if(result.hasErrors()){

            List<FieldError> errorList = result.getFieldErrors();
            for(FieldError error : errorList){
                redirectAttributes.addFlashAttribute("error", error.getCode());;
                break;
            }
            return "redirect:/user/update";
        }

        // error 없으면 비밀번호 수정
        String page = "/user/updateOk";
        int cnt = userService.updatePw(user);
        model.addAttribute("result", cnt);
        return page;
    }

    @GetMapping("/delete")
    public void delete(Model model){
        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());;
    }

    @PostMapping("/delete")
    public String deleteOk(User user, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
        if(userService.pwCheck(user)) {

            PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user1 = userDetails.getUser();
            Long id = user1.getId();

            if (id != null) {
                int cnt = userService.delete(id);
                model.addAttribute("result", cnt);
            }
            // Spring Security의 로그아웃 처리를 호출하여 로그아웃
            new SecurityContextLogoutHandler().logout(request, response, null);
            return "/user/deleteOk";
        } else {
            redirectAttributes.addFlashAttribute("error", "비밀번호가 틀렸습니다.");
            return "redirect:/user/delete";
        }
    }

    @GetMapping("/myPage")
    public void myPage(Model model) {
        // 로그인한 사용자 정보를 모델에 추가하여 페이지에 표시
        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        model.addAttribute("id", user.getId());
        model.addAttribute("username",user.getUsername());
    }

    @GetMapping("/wishList")
    public @ResponseBody List<String> wishList(){
        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Long id = user.getId();
        return userService.findWishListById(id);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {binder.setValidator(new UserValidator());}




}
