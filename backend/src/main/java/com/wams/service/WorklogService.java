package com.wams.service;

import com.wams.model.Worklog;
import com.wams.model.User;
import com.wams.repository.WorklogRepository;
import com.wams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WorklogService {

    @Autowired
    private WorklogRepository worklogRepository;

    @Autowired
    private UserRepository userRepository;

    public Worklog addWorklog(Long userId, Worklog worklog) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            worklog.setEmployee(userOpt.get());
            return worklogRepository.save(worklog);
        }
        throw new RuntimeException("User not found");
    }

    public List<Worklog> getWorklogsByUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(worklogRepository::findByEmployee)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Worklog> getWorklogsByUserBetweenDates(Long userId, LocalDate start, LocalDate end) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(user -> worklogRepository.findByEmployeeAndDateBetween(user, start, end))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
