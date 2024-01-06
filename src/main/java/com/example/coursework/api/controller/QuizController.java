package com.example.coursework.api.controller;

import com.example.coursework.api.service.QuizService;
import com.example.coursework.api.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizService service;

    @Autowired
    public QuizController(QuizService service) {
        this.service = service;
    }

    @Async
    @GetMapping
    public CompletableFuture<List<Quiz>> getAll() {
        return service.getAllQuiz();
    }

    @Async
    @GetMapping("/{id}")
    public CompletableFuture<Quiz> getById(@PathVariable Integer id) {
        return service.getQuizById(id);
    }

    @Async
    @PostMapping
    public CompletableFuture<Quiz> newQuiz(@RequestBody Quiz newQuestion) {
        return service.createQuiz(newQuestion);
    }

    @Async
    @PutMapping("/{id}")
    public CompletableFuture<Quiz> replaceQuiz(@RequestBody Quiz newQuestion, @PathVariable Integer id) {
        return service.updateQuiz(id, newQuestion);
    }

    @Async
    @DeleteMapping("/{id}")
    public CompletableFuture<Void> deleteQuiz(@PathVariable Integer id) {
        return service.deleteQuiz(id);
    }
}
