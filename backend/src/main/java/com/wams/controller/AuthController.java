package com.wams.controller;

import com.wams.model.User;
import com.wams.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        User user = userService.getByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            Map<String, Object> res = new HashMap<>();
            res.put("userId", user.getId());
            res.put("username", user.getUsername());
            res.put("role", user.getRole());
            return ResponseEntity.ok(res);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
