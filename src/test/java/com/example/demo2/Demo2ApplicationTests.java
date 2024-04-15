package com.example.demo2;

import com.example.demo2.answer.AnswerService;
import com.example.demo2.question.Question;
import com.example.demo2.question.QuestionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class Demo2ApplicationTests {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private AnswerService answerService;

	@Test
	@Transactional
	@Rollback(value = false)
	void contextLoads() {
		Question q1 = this.questionService.create("sbb가 무엇인가요?","sbb에 대해서 알고 싶습니다.",null);
		Question q2 = this.questionService.create("스프링부트 모델 질문입니다.","id는 자동으로 생성되나요?",null);

		this.answerService.create(q1, "알려드릴게요",null);
		this.answerService.create(q2, "네 자동으로 생성 됩니다.",null);
	}
	@Test
	@Transactional
	@Rollback(value = false)
	void testJpa() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			this.questionService.create(subject, content,null);
		}
	}

}
