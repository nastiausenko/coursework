package com.example.coursework.api.service;

import com.example.coursework.api.model.Question;
import com.example.coursework.exceptions.QuestionNotFoundException;
import com.example.coursework.repos.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class QuestionService {

    private final QuestionRepository repository;

    @Autowired
    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    @Async
    public CompletableFuture<List<Question>> getAllQuestions() {
        return CompletableFuture.supplyAsync(repository::findAll);
    }

    @Async
    public CompletableFuture<Question> getQuestionById(Integer id) {
        return CompletableFuture.supplyAsync(() -> repository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id)));
    }

    @Async
    public CompletableFuture<Question> createQuestion(Question newQuestion) {
        return CompletableFuture.supplyAsync(() -> repository.save(newQuestion));
    }

    @Async
    public CompletableFuture<Question> updateQuestion(Integer id, Question newQuestion) {
        return CompletableFuture.supplyAsync(() ->
                repository.findById(id)
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
                        })
        );
    }

    @Async
    public CompletableFuture<Void> deleteQuestion(Integer id) {
        return CompletableFuture.runAsync(() -> {
            if (repository.existsById(id)) {
                repository.deleteById(id);
            } else {
                throw new QuestionNotFoundException(id);
            }
        });
    }
}
