package com.example.coursework.api.controller;

import com.example.coursework.exeptions.QuizNotFoundExeption;
import com.example.coursework.api.model.Quiz;
import com.example.coursework.repos.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuizController {

    private final QuizRepository repository;

    @Autowired
    public QuizController(QuizRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/quizzes")
    public List<Quiz> getAll() {
        return repository.findAll();
    }

    @GetMapping("/quizzes/{id}")
    public Quiz getById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new QuizNotFoundExeption(id));
    }

    @PostMapping("/quizzes")
    public Quiz newQuiz(@RequestBody Quiz newQuiz) {
        return repository.save(newQuiz);
    }

    @PutMapping("/quizzes/{id}")
    public Quiz replaceQuiz(@RequestBody Quiz newQuiz, @PathVariable Integer id) {
        return repository.findById(id)
                .map(quiz -> {
                    quiz.setName(newQuiz.getName());
                    quiz.setDescription(newQuiz.getDescription());
                    return repository.save(quiz);
                })
                .orElseGet(() -> {
                    newQuiz.setId(id);
                    return repository.save(newQuiz);
                });
    }

    @DeleteMapping("/quizzes/{id}")
    void deleteQuiz(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
