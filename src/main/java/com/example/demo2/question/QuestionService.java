package com.example.demo2.question;

import com.example.demo2.DataNotFoundException;
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
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>(); // Sort 는 데이터를 정렬하는 과정이며 sorts 객체 안에 정렬과정을 넣을 수 있음
        sorts.add(Sort.Order.desc("createDate")); // sorts 객체 안에 createDate 를 desc 순으로 정렬하는 과정 추가
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 매개변수 page(해당 페이지)에 갯수(게시물개수)를 Sort.by(sorts(정렬과정)) 순으로 정렬
        return this.questionRepository.findAll(pageable); // pageable 객체에 정의 되어있는 정렬방식으로 모든 question 객체를 찾음.
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public Question create(String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        return this.questionRepository.save(q);
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }
    public void vote (Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
}