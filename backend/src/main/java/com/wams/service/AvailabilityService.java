package com.wams.service;

import com.wams.model.Availability;
import com.wams.model.User;
import com.wams.repository.AvailabilityRepository;
import com.wams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private UserRepository userRepository;

    public Availability submitAvailability(Long userId, Availability availability) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            availability.setEmployee(userOpt.get());
            return availabilityRepository.save(availability);
        }
        throw new RuntimeException("User not found with ID: " + userId);
    }

    public List<Availability> getAvailabilityByUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(availabilityRepository::findByEmployee)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean isAvailableOnDate(Long userId, LocalDate date) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(user -> !availabilityRepository.findByEmployeeAndDate(user, date).isEmpty())
                .orElse(false);
    }

    public List<Availability> getAvailabilityBetween(Long userId, LocalDate start, LocalDate end) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(user -> availabilityRepository.findByEmployeeAndDateBetween(user, start, end))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
