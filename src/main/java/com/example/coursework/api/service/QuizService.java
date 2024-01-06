package com.example.coursework.api.service;

import com.example.coursework.api.model.Quiz;
import com.example.coursework.exceptions.QuizNotFoundException;
import com.example.coursework.repos.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    private final QuizRepository repository;

    @Autowired
    public QuizService(QuizRepository repository) {
        this.repository = repository;
    }

    public List<Quiz> getAllQuiz() {
        return repository.findAll();
    }

    public Quiz getQuizById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new QuizNotFoundException(id));
    }

    public Quiz createQuiz(Quiz newQuiz) {
        return repository.save(newQuiz);
    }

    public Quiz updateQuiz(Integer id, Quiz newQuiz) {
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

    public Void deleteQuiz(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new QuizNotFoundException(id);
        }
        return null;
    }
}
