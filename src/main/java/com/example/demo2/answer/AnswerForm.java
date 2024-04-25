package com.example.demo2.answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {

    @NotEmpty(message = "내용을 입력해주세요.") // @Valid 유효성 검사후 예외 발생시 해당 메세지 출력
    private String content;
}
