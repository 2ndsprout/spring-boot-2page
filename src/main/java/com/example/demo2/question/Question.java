package com.example.demo2.question;

import com.example.demo2.answer.Answer;
import com.example.demo2.comment.Comment;
import com.example.demo2.user.SiteUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql 의 autoIncrement 와 같은 고유하고 중복 되지 않는 값
    private Integer id;

    @Column(length = 200) // column 에서 문자열 길이가 200을 넘을 수 없다.
    private String subject;

    @Column(columnDefinition = "TEXT") // column 에서 문자열로 나타낸다.
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // CascadeType.REMOVE 로 해당 question 이 삭제 되었을때 연관매핑 되어있는 answerList 도 같이 삭제된다.
    private List<Answer> answerList = new ArrayList<>();

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<SiteUser> voter; // set 자료형은 중복을 허용하지 않는 고유한 값들의 집합을 나타내는 인터페이스
                         // 하나의 답글에 한명의 사용자가 추천을 중복으로 할 수 없게 할 수 있다.

    @OneToMany(mappedBy = "question")
    private List<Comment> commentList = new ArrayList<>();

}
