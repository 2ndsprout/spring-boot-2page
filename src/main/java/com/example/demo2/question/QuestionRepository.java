package com.example.demo2.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Page<Question> findAll (Pageable pageable);
    // Question 을 pageable 방식으로 Page 를 만드는 메서드 추가
}
