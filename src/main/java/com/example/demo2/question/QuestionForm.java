package com.example.demo2.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {

    @NotEmpty(message = "제목을 입력해주세요.") // @Valid 유효성 검사후 예외 발생시 해당 메세지 출력
    @Size(max = 200, message = "200자 내로 작성해주세요") // 해당 문자열의 길이를 200으로 제한. 200을 넘을 시 해당 메시지 출력
    private String subject;

    @NotEmpty(message = "내용을 입력해주세요.") // @Valid 유효성 검사후 예외 발생시 해당 메세지 출력
    private String content;
}
