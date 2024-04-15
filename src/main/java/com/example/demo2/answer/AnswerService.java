package com.example.demo2.answer;

import com.example.demo2.question.Question;
import com.example.demo2.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer create (Question question, String content, SiteUser siteUser) {
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setContent(content);
        answer.setAuthor(siteUser);
        answer.setCreateDate(LocalDateTime.now());

        return this.answerRepository.save(answer);
    }
}
