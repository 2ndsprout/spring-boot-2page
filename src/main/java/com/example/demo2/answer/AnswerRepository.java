package com.example.demo2.answer;

import com.example.demo2.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    Page<Answer> findAllByQuestion(Question question, Pageable pageable);
    // Question 객체 안의 answerList 를 pageable 방식으로 Page 를 만드는 메서드 추가
}
