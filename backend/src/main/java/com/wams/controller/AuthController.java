package com.wams.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wams.dto.LoginRequest;
import com.wams.dto.LoginResponse;
import com.wams.model.User;
import com.wams.repository.UserRepository;

@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            LoginResponse response = new LoginResponse(user.getUsername(), user.getRole().name() );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}
