package com.example.coursework.exeptions;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Integer id) {
        super("Question not found " + id);
    }
}
