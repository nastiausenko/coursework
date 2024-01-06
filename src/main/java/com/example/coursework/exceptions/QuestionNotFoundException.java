package com.example.coursework.exceptions;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Integer id) {
        super("Question not found " + id);
    }
}
