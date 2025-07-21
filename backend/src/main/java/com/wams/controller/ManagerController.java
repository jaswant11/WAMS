package com.wams.controller;

import com.wams.model.User;
import com.wams.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    private UserService userService;

    // Get all managers
    @GetMapping
    public ResponseEntity<List<User>> getAllManagers() {
        List<User> managers = userService.getAllUsers().stream()
            .filter(user -> "manager".equalsIgnoreCase(user.getRole()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(managers);
    }

    // Get manager by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getManagerById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Update manager profile
    @PutMapping("/{id}")
    public ResponseEntity<User> updateManager(@PathVariable Long id, @RequestBody User updated) {
        return ResponseEntity.ok(userService.updateUser(id, updated));
    }
}
