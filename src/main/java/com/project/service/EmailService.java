package com.project.service;

public interface EmailService {
    String sendEmail(String email)throws Exception;

    String findUsernameBySendEmail(String email) throws Exception;
}
