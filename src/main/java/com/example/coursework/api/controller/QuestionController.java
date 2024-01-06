package com.example.coursework.api.controller;

import com.example.coursework.api.model.Question;
import com.example.coursework.exceptions.QuestionNotFoundException;
import com.example.coursework.repos.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class QuestionController {

    private final QuestionRepository repository;

    @Autowired
    public QuestionController(QuestionRepository repository) {
        this.repository = repository;
    }

    @Async
    @GetMapping("/questions")
    public CompletableFuture<List<Question>> getAll() {
        return CompletableFuture.completedFuture(repository.findAll());
    }

    @Async
    @GetMapping("/questions/{id}")
    public CompletableFuture<Question> getById(@PathVariable Integer id) {
        return CompletableFuture.completedFuture(repository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id)));
    }

    @Async
    @PostMapping("/questions")
    public CompletableFuture<Question> newQuestion(@RequestBody Question newQuestion) {
        return CompletableFuture.completedFuture(repository.save(newQuestion));
    }

    @Async
    @PutMapping("/questions/{id}")
    public CompletableFuture<Question> replaceQuestion(@RequestBody Question newQuestion, @PathVariable Integer id) {
        return CompletableFuture.completedFuture(repository.findById(id)
                .map(question -> {
                    question.setType(newQuestion.getType());
                    question.setNumber(newQuestion.getNumber());
                    question.setDescription(newQuestion.getDescription());
                    question.setQuiz(newQuestion.getQuiz());
                    return repository.save(question);
                })
                .orElseGet(() -> {
                    newQuestion.setId(id);
                    return repository.save(newQuestion);
                }));
    }

    @DeleteMapping("/questions/{id}")
    public CompletableFuture<Void> deleteQuestion(@PathVariable Integer id) throws QuestionNotFoundException {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return CompletableFuture.completedFuture(null);
        } else throw new QuestionNotFoundException(id);
    }
}
