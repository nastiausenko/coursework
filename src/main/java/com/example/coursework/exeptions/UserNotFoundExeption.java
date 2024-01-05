package com.example.coursework.exeptions;

public class UserNotFoundExeption extends RuntimeException{
    public UserNotFoundExeption(Integer id) {
        super("User not found " + id);
    }
}
