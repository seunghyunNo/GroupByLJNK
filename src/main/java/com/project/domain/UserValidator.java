package com.project.domain;

import com.project.domain.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        // 검증할 데이터가 User 클래스 타입인지 확인
        boolean result = User.class.isAssignableFrom(clazz);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;

        // 유효성검사 errorMessage
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "아이디은 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "비밀번호는 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "이메일은 필수입니다.");

        // password 와 re_password 가 같지 않으면 errorMessage 등록
        if(!user.getPassword().equals(user.getRe_password())){
            errors.rejectValue("re_password", "비밀번호와 비밀번호확인은 같아야 합니다.");
        }


    }
}
