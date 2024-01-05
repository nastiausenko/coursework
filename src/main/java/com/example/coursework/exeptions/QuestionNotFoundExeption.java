package com.example.coursework.exeptions;

public class QuestionNotFoundExeption extends RuntimeException {
    public QuestionNotFoundExeption(Integer id) {
        super("Question not found " + id);
    }
}
