package com.example.coursework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class QuizNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(QuizNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String quizNotFoundHandler(QuizNotFoundException ex) {
    return ex.getMessage();
  }
}