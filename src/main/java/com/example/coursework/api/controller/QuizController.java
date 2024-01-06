package com.example.coursework.api.controller;

import com.example.coursework.api.service.QuizService;
import com.example.coursework.api.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class QuizController {

    private final QuizService service;

    @Autowired
    public QuizController(QuizService service) {
        this.service = service;
    }

    @Async
    @GetMapping("/quizzes")
    public CompletableFuture<List<Quiz>> getAll() {
        return CompletableFuture.completedFuture(service.getAllQuiz());
    }

    @Async
    @GetMapping("/quizzes/{id}")
    public CompletableFuture<Quiz> getById(@PathVariable Integer id) {
        return CompletableFuture.completedFuture(service.getQuizById(id));
    }

    @Async
    @PostMapping("/quizzes")
    public CompletableFuture<Quiz> newQuiz(@RequestBody Quiz newQuestion) {
        return CompletableFuture.completedFuture(service.createQuiz(newQuestion));
    }

    @Async
    @PutMapping("/quizzes/{id}")
    public CompletableFuture<Quiz> updateQuiz(@RequestBody Quiz newQuestion, @PathVariable Integer id) {
        return CompletableFuture.completedFuture(service.updateQuiz(id, newQuestion));
    }

    @Async
    @DeleteMapping("/quizzes/{id}")
    public CompletableFuture<Void> deleteQuiz(@PathVariable Integer id) {
        return CompletableFuture.completedFuture(service.deleteQuiz(id));
    }
}
