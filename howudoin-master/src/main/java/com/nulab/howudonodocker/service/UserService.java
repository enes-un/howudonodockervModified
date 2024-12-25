package com.nulab.howudonodocker.service;

import com.nulab.howudonodocker.SimpleJwt;
import com.nulab.howudonodocker.model.User;
import com.nulab.howudonodocker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        if (!Objects.equals(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Return a success message or basic user info
        return SimpleJwt.createToken(email, password);
    }
}
