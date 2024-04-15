package com.example.demo2.question;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class QuestionForm {

    @NotEmpty(message = "제목을 입력해주세요.")
    private String subject;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;
}
