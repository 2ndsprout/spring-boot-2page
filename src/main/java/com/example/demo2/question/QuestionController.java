package com.example.demo2.question;

import com.example.demo2.answer.Answer;
import com.example.demo2.answer.AnswerForm;
import com.example.demo2.answer.AnswerService;
import com.example.demo2.user.SiteUser;
import com.example.demo2.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("question")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;
    private final AnswerService answerService;

    @GetMapping("/list") // @GetMapping: HTTP GET 요청을 처리하며, 주로 데이터 조회 및 페이지 로드에 사용됩니다.
                         // @PostMapping: HTTP POST 요청을 처리하며, 주로 데이터 제출 및 변경에 사용됩니다.
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page) { // page 매개변수는 기본적으로 0

        Page<Question> paging = this.questionService.getList(page); // page 매개변수(해당하는 페이지) 로 page 에 맞는 게시물을 조회
        model.addAttribute("paging", paging); // model 객체에 paging 이라는 값으로 template 과 연결
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model,
                         @PathVariable("id") Integer id,        // URL 경로에서 값을 추출하여 메서드 매개변수에 매핑할 때 사용
                         AnswerForm answerForm,                 // question_detail.html 에서 answerForm 을 object 로 사용하기때문에 필요. model 객체 없이도 template 에 전달 가능
                         @RequestParam(value = "answerPage", defaultValue = "0") int answerPage) { // answerPage 매개변수 (해당하는 페이지) 로 answerPage 에 맞는 게시물을 조회
        Question question = this.questionService.getQuestion(id);
        Page<Answer> answerPaging = this.answerService.getList(question, answerPage); // 해당 question 에 연관매핑 되어있는 answer 객체들을 해당 answerPage 변수에 맞게 조회
        model.addAttribute("question", question); // model 객체에 question 이라는 값으로 template 와 연결
        model.addAttribute("answerPaging", answerPaging); // model 객체에 answerPaging 이라는 값으로 template 와 연결
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()") // 특정 메서드 또는 클래스에 접근하기 전에 사용자가 인증되었는지 확인하는 데 사용 인증 되지 않으면 로그인 화면으로 돌아감.
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) { // question_form.html 에서 questionForm 을 Object 로 사용하고 있음 매개변수에 추가.
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm,
                                 BindingResult bindingResult,
                                 Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal,
                                 @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote (Principal principal, @PathVariable("id") Integer id) {

        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return "redirect:/question/detail/%d".formatted(id);
    }
}