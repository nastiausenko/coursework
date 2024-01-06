package com.example.coursework.api.service;

import com.example.coursework.api.model.Question;
import com.example.coursework.exceptions.QuestionNotFoundException;
import com.example.coursework.repos.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository repository;

    @Autowired
    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public List<Question> getAllQuestions() {
        return repository.findAll();
    }

    public Question getQuestionById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
    }

    public Question createQuestion(Question newQuestion) {
        return repository.save(newQuestion);
    }

    public Question updateQuestion(Integer id, Question newQuestion) {
        return repository.findById(id)
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
                });
    }

    public Void deleteQuestion(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new QuestionNotFoundException(id);
        }
        return null;
    }
}
