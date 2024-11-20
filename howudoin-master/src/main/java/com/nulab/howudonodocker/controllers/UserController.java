package com.nulab.howudonodocker.controllers;

import com.nulab.howudonodocker.SimpleJwt;
import com.nulab.howudonodocker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nulab.howudonodocker.model.User;
import com.nulab.howudonodocker.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import com.nulab.howudonodocker.SimpleJwt;
@RestController
//@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String createUser(@RequestBody User user) {
        userRepository.save(user);
        return  SimpleJwt.createToken(user.getEmail(), user.getPassword());
    }

    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {
        System.out.println(userService.login(loginRequest.getEmail(), loginRequest.getPassword()));
        return SimpleJwt.createToken(loginRequest.getEmail(), loginRequest.getPassword());
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