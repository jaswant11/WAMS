package com.wams.service;

import com.wams.model.TimeOff;
import com.wams.model.User;
import com.wams.repository.TimeOffRepository;
import com.wams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TimeOffService {

    @Autowired
    private TimeOffRepository timeOffRepository;

    @Autowired
    private UserRepository userRepository;

    public TimeOff requestTimeOff(Long userId, TimeOff timeOff) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            timeOff.setEmployee(user.get());
            return timeOffRepository.save(timeOff);
        }
        throw new RuntimeException("User not found");
    }

    public List<TimeOff> getTimeOffsByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return timeOffRepository.findByEmployee(user.get());
        }
        throw new RuntimeException("User not found");
    }

    public boolean hasTimeOffOnDate(Long userId, LocalDate date) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<TimeOff> timeOffs = timeOffRepository
                .findByEmployeeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(user.get(), date, date);
            return !timeOffs.isEmpty();
        }
        return false;
    }
}
