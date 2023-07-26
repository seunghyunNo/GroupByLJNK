package com.project.controller;

import com.project.domain.EmailCheckReq;
import com.project.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController {

    @Autowired
    private EmailService emailService;

    // 이메일 인증
    @ResponseBody
    @PostMapping("/signup/authEmail")
    public ResponseEntity<String> EmailCheck(@RequestBody EmailCheckReq emailCheckReq) throws Exception {
        String authCode = emailService.sendEmail(emailCheckReq.getEmail());
        return ResponseEntity.ok(authCode);	// Response body 에 값을 반환
    }

    @ResponseBody
    @PostMapping("/user/findFullUsername")
    public ResponseEntity<String> findId(@RequestBody EmailCheckReq emailCheckReq) throws Exception{
        String email = emailService.findUsernameBySendEmail(emailCheckReq.getEmail());
        return ResponseEntity.ok(email);
    }



}
