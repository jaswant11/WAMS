package com.wams.controller;

import com.wams.model.User;
import com.wams.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        return userService.findByEmail(loginRequest.getEmail())
            .filter(user -> user.getPassword().equals(loginRequest.getPassword()))
            .map(user -> (ResponseEntity<?>) ResponseEntity.ok(user))
            .orElseGet(() -> ResponseEntity.status(401).body("Invalid email or password"));
    }
}
