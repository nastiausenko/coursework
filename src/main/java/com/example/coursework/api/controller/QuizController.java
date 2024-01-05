package com.example.coursework.api.controller;

import com.example.coursework.exeptions.QuizNotFoundException;
import com.example.coursework.api.model.Quiz;
import com.example.coursework.repos.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class QuizController {

    private final QuizRepository repository;

    @Autowired
    public QuizController(QuizRepository repository) {
        this.repository = repository;
    }

    @Async
    @GetMapping("/quizzes")
    public CompletableFuture<List<Quiz>> getAll() {
        return CompletableFuture.completedFuture(repository.findAll());
    }

    @Async
    @GetMapping("/quizzes/{id}")
    public CompletableFuture<Quiz> getById(@PathVariable Integer id) {
        return CompletableFuture.completedFuture(repository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id)));
    }

    @Async
    @PostMapping("/quizzes")
    public CompletableFuture<Quiz> newQuiz(@RequestBody Quiz newQuiz) {
        return CompletableFuture.completedFuture(repository.save(newQuiz));
    }

    @Async
    @PutMapping("/quizzes/{id}")
    public CompletableFuture<Quiz> replaceQuiz(@RequestBody Quiz newQuiz, @PathVariable Integer id) {
        return CompletableFuture.completedFuture(repository.findById(id)
                .map(quiz -> {
                    quiz.setName(newQuiz.getName());
                    quiz.setDescription(newQuiz.getDescription());
                    return repository.save(quiz);
                })
                .orElseGet(() -> {
                    newQuiz.setId(id);
                    return repository.save(newQuiz);
                }));
    }

    @Async
    @DeleteMapping("/quizzes/{id}")
    public CompletableFuture<Void> deleteQuiz(@PathVariable Integer id) {
        repository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }
}
