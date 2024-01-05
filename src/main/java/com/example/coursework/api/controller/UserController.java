package com.example.coursework.api.controller;

import com.example.coursework.api.model.User;
import com.example.coursework.exeptions.UserNotFoundExeption;
import com.example.coursework.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    public List<User> getAll() {
        return repository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundExeption(id));
    }

    @PostMapping("/users")
    public User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @PutMapping("/users/{id}")
    public User replaceUser(@RequestBody User newUser, @PathVariable Integer id) {
        return repository.findById(id)
                .map(user -> {
                    user.setPassword(newUser.getPassword());
                    user.setName(newUser.getName());
                    user.setSurname(newUser.getSurname());
                    user.setNickname(newUser.getNickname());
                    user.setEmail(newUser.getEmail());
                    user.setPicture(newUser.getPicture());
                    user.setRole(newUser.getRole());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
