package com.project.service;

import java.util.Random;


import com.project.domain.User;
import com.project.repository.AuthorityRepository;
import com.project.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender emailSender;

    private UserRepository userRepository;

    @Autowired
    public EmailServiceImpl(SqlSession session){
        userRepository = session.getMapper(UserRepository.class);
    }

    public static final String createCode = createKey();

    private MimeMessage createMessage(String email)throws Exception{

        String setFrom = "dldls458@gmail.com";	// 보내는 사람
        String toEmail = email;		// 받는 사람(값 받아옵니다.)
        String title = "SteamHub 인증번호";		// 메일 제목

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);	// 받는 사람 설정
        message.setSubject(title);		// 제목 설정

        // 메일 내용 설정
        String sendMsg="";
        sendMsg += "<div style='margin:20px;'>";
        sendMsg += "<h1> 안녕하세요 SteamHub 입니다. </h1>";
        sendMsg += "<br>";
        sendMsg += "<p>아래 코드를 입력해주세요<p>";
        sendMsg += "<br>";
        sendMsg += "<p>감사합니다.<p>";
        sendMsg += "<br>";
        sendMsg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        sendMsg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        sendMsg += "<div style='font-size:130%'>";
        sendMsg += "CODE : <strong>";
        sendMsg += createCode + "</strong><div><br/> ";
        sendMsg += "</div>";

        message.setFrom(setFrom);		// 보내는 사람 설정
        // 위 String 으로 받은 내용을 아래에 넣어 내용을 설정
        message.setText(sendMsg, "utf-8", "html");

        return message;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }
    @Override
    public String sendEmail(String email)throws Exception {

        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createMessage(email);
        //실제 메일 전송
        emailSender.send(emailForm);

        return createCode; //인증 코드 반환
    }

    @Override
    public String findUsernameBySendEmail(String email) throws Exception {

        // 메일전송에 필요한 정보 설정
        MimeMessage emailForm = findUsername(email);
        // 메일 전송
        emailSender.send(emailForm);

        return email;
    }

    private MimeMessage findUsername(String email)throws Exception{

        String setFrom = "dldls458@gmail.com";	// 보내는 사람
        String toEmail = email;		// 받는 사람(값 받아옵니다.)
        String title = "SteamHub 아이디 찾기 결과";		// 메일 제목

        MimeMessage message = emailSender.createMimeMessage();
        User user = userRepository.findByEmail(email);
        String username = user.getUsername();

        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);	// 받는 사람 설정
        message.setSubject(title);		// 제목 설정

        // 메일 내용 설정
        String sendMsg="";
        sendMsg += "<div style='margin:20px;'>";
        sendMsg += "<h1> 안녕하세요 SteamHub 입니다. </h1>";
        sendMsg += "<br>";
        sendMsg += "<p>귀하의 아이디는 아래와 같습니다<p>";
        sendMsg += "<br>";
        sendMsg += "<p>감사합니다.<p>";
        sendMsg += "<br>";
        sendMsg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        sendMsg += "<div style='font-size:130%'>";
        sendMsg += "아이디 : <strong>";
        sendMsg += username + "</strong><div><br/> ";
        sendMsg += "</div>";

        message.setFrom(setFrom);		// 보내는 사람 설정
        // 위 String 으로 받은 내용을 아래에 넣어 내용을 설정
        message.setText(sendMsg, "utf-8", "html");

        return message;
    }

}

