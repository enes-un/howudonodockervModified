package com.nulab.howudonodocker.controllers;

import com.nulab.howudonodocker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nulab.howudonodocker.model.User;
import com.nulab.howudonodocker.repository.UserRepository;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {
        return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userRepository.findById(id).orElse(null);
    }
}