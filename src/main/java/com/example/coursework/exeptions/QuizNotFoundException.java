package com.example.coursework.exeptions;

public class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException(Integer id) {
        super("Quiz not found " + id);
    }
}
