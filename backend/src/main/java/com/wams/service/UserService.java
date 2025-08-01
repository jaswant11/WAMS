package com.wams.service;

import com.wams.model.User;
import com.wams.model.Employee;
import com.wams.model.Manager;
import com.wams.repository.UserRepository;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.ManagerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        User savedUser = userRepository.save(user);

        if (user.getRole() == User.Role.EMPLOYEE) {
            Employee emp = new Employee();
            emp.setId(savedUser.getId());
            emp.setUsername(savedUser.getUsername());
            employeeRepository.save(emp);
        } else if (user.getRole() == User.Role.MANAGER) {
            Manager mgr = new Manager();
            mgr.setId(savedUser.getId());
            mgr.setUsername(savedUser.getUsername());
            managerRepository.save(mgr);
        }

        return savedUser;
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existing = optionalUser.get();
            existing.setUsername(updatedUser.getUsername());
            existing.setPassword(updatedUser.getPassword());
            existing.setRole(updatedUser.getRole());
            return userRepository.save(existing);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (user.getRole() == User.Role.EMPLOYEE) {
                employeeRepository.deleteById(id);
            } else if (user.getRole() == User.Role.MANAGER) {
                managerRepository.deleteById(id);
            }

            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
