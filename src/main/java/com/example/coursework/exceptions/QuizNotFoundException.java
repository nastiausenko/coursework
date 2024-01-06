package com.example.coursework.exceptions;

public class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException(Integer id) {
        super("Quiz not found " + id);
    }
}
