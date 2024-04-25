package com.example.demo2.answer;

import com.example.demo2.DataNotFoundException;
import com.example.demo2.question.Question;
import com.example.demo2.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Page<Answer> getList(Question question, int page) {
        List<Sort.Order> sorts = new ArrayList<>(); // Sort 는 데이터를 정렬하는 과정이며 sorts 객체 안에 정렬과정을 넣을 수 있음
        sorts.add(Sort.Order.desc("voter")); // sorts 객체 안에 voter 을 desc 순으로 정렬하는 과정 추가
        sorts.add(Sort.Order.desc("createDate")); // sorts 객체 안에 createDate 을 desc 순으로 정렬하는 과정 추가
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts)); // 매개변수 page(해당 페이지)에 갯수(게시물개수)를 Sort.by(sorts(정렬과정)) 순으로 정렬
        return this.answerRepository.findAllByQuestion(question, pageable); // 해당 Question 안의 answerList 를 pageable 에 정의 한 정렬 방식으로 정렬
    }

    public Answer create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        return this.answerRepository.save(answer);
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }
    public void vote (Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }
}