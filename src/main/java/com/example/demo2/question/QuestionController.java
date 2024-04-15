package com.example.demo2.question;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list (Model model) {
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail (@PathVariable("id")Integer id, Model model) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);

        return "question_detail";
    }
    @GetMapping("/create")
    public String create (QuestionForm questionForm) {
        return "question_form";
    }
    @PostMapping("create")
    public String create (@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(),questionForm.getContent());
        return "redirect:/question/list";
    }
}
