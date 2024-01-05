package com.example.coursework.api.controller;

import com.example.coursework.api.model.Question;
import com.example.coursework.exeptions.QuestionNotFoundExeption;
import com.example.coursework.repos.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {

    private final QuestionRepository repository;

    @Autowired
    public QuestionController(QuestionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/questions")
    public List<Question> getAll() {
        return repository.findAll();
    }

    @GetMapping("/questions/{id}")
    public Question getById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundExeption(id));
    }

    @PostMapping("/questions")
    public Question newQuestion(@RequestBody Question newQuestion) {
        return repository.save(newQuestion);
    }

    @PutMapping("/questions/{id}")
    public Question replaceQuestion(@RequestBody Question newQuestion, @PathVariable Integer id) {
        return repository.findById(id)
                .map(question -> {
                    question.setType(newQuestion.getType());
                    question.setNumber(newQuestion.getNumber());
                    question.setDescription(newQuestion.getDescription());
                    return repository.save(question);
                })
                .orElseGet(() -> {
                    newQuestion.setId(id);
                    return repository.save(newQuestion);
                });
    }

    @DeleteMapping("/questions/{id}")
    void deleteQuestion(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
