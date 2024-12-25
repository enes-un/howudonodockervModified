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
import com.nulab.howudonodocker.model.ApiResponse;
import org.springframework.http.ResponseEntity;
@RestController
//@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> createUser(@RequestBody User user) {
        userRepository.save(user);
        String token = SimpleJwt.createToken(user.getEmail(), user.getPassword());
        return ResponseEntity.ok(new ApiResponse<>(200, "User registered successfully", token));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody User loginRequest) {
        try {
            // Attempt login and generate token
            String token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(new ApiResponse<>(200, "Login successful", token));
        } catch (RuntimeException e) {
            // Catch the exception and return an appropriate response
            return ResponseEntity.status(401).body(new ApiResponse<>(401, e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Users retrieved successfully", users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable String id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(200, "User retrieved successfully", user)))
                .orElseGet(() -> ResponseEntity.status(404).body(new ApiResponse<>(404, "User not found", null)));
    }
}