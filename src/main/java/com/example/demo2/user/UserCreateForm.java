package com.example.demo2.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {

    @NotEmpty(message = "사용자ID는 필수 입력항목입니다.")
    @Size(min = 3, max = 25)
    private String username;

    @NotEmpty(message = "비밀번호는 필수 입력항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호를 확인해주세요.")
    private String password2;

    @NotEmpty(message = "이메일은 필수 입력항목입니다.")
    @Email
    private String email;
}
