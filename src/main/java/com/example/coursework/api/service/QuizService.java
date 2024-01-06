package com.example.coursework.api.service;

import com.example.coursework.api.model.Quiz;
import com.example.coursework.exceptions.QuizNotFoundException;
import com.example.coursework.repos.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class QuizService {
    private final QuizRepository repository;

    @Autowired
    public QuizService(QuizRepository repository) {
        this.repository = repository;
    }

    @Async
    public CompletableFuture<List<Quiz>> getAllQuiz() {
        return CompletableFuture.supplyAsync(repository::findAll);
    }

    @Async
    public CompletableFuture<Quiz> getQuizById(Integer id) {
        return CompletableFuture.supplyAsync(() -> repository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id)));
    }

    @Async
    public CompletableFuture<Quiz> createQuiz(Quiz newQuiz) {
        return CompletableFuture.supplyAsync(() -> repository.save(newQuiz));
    }

    @Async
    public CompletableFuture<Quiz> updateQuiz(Integer id, Quiz newQuiz) {
        return CompletableFuture.supplyAsync(() -> repository.findById(id)
                .map(quiz -> {
                    quiz.setName(newQuiz.getName());
                    quiz.setDescription(newQuiz.getDescription());
                    return repository.save(quiz);
                })
                .orElseGet(() -> {
                    newQuiz.setId(id);
                    return repository.save(newQuiz);
                })
        );
    }

    @Async
    public CompletableFuture<Void> deleteQuiz(Integer id) {
        return CompletableFuture.runAsync(() -> {
            if (repository.existsById(id)) {
                repository.deleteById(id);
            } else {
                throw new QuizNotFoundException(id);
            }
        });
    }
}
