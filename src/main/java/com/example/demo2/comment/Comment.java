package com.example.demo2.comment;

import com.example.demo2.answer.Answer;
import com.example.demo2.question.Question;
import com.example.demo2.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private SiteUser author;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private Answer answer;

    public Integer getQuestionId() {
        Integer result = null;                              // result 를 null 로 변수 초기화 한다.
        if (this.question != null) {                        // 해당 question 이 null 이 아닐때 result 는 question 의 id와 같은 값을 가진다.
            result = this.question.getId();
        } else if (this.answer != null) {                   // 해당 answer 가 null 이 아닐때 result 는 answer 의 question 의 id와 같은 값을 가진다.
            result = this.answer.getQuestion().getId();
        }
        return result;
    }
}
