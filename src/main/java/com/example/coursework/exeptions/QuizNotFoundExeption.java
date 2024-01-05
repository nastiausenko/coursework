package com.example.coursework.exeptions;

public class QuizNotFoundExeption extends RuntimeException {

    public QuizNotFoundExeption(Integer id) {
        super("Quiz not found " + id);
    }
}
