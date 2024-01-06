package com.example.coursework.api.controller;

import com.example.coursework.api.model.Question;
import com.example.coursework.api.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Async
    @GetMapping
    public CompletableFuture<List<Question>> getAll() {
        return questionService.getAllQuestions();
    }

    @Async
    @GetMapping("/{id}")
    public CompletableFuture<Question> getById(@PathVariable Integer id) {
        return questionService.getQuestionById(id);
    }

    @Async
    @PostMapping
    public CompletableFuture<Question> newQuestion(@RequestBody Question newQuestion) {
        return questionService.createQuestion(newQuestion);
    }

    @Async
    @PutMapping("/{id}")
    public CompletableFuture<Question> replaceQuestion(@RequestBody Question newQuestion, @PathVariable Integer id) {
        return questionService.updateQuestion(id, newQuestion);
    }

    @Async
    @DeleteMapping("/{id}")
    public CompletableFuture<Void> deleteQuestion(@PathVariable Integer id) {
        return questionService.deleteQuestion(id);
    }
}
