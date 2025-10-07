package com.joelcode.pokerchipsapplication.service;


import com.joelcode.pokerchipsapplication.entities.User;
import com.joelcode.pokerchipsapplication.repositories.UserRepo;
import com.joelcode.pokerchipsapplication.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // ==== USER CREATION & AUTHENTICATION ====

    public User createUser(String username, String email, String password) throws Exception {
        if (userRepo.existsByUsername(username)){
            throw new Exception("Username already exists");
        }

        if (userRepo.existsByEmail(email)){
            throw new Exception("Email already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        return userRepo.save(user);
    }

    public String authenticateUser(String username, String password){
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Invalid username or password");
        }

        return tokenProvider.generateToken(user);
    }

    public User findById(UUID id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
