package com.wams.service;

import com.wams.model.Shift;
import com.wams.model.User;
import com.wams.repository.ShiftRepository;
import com.wams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private UserRepository userRepository;

    public Shift createShift(Shift shift) {
        return shiftRepository.save(shift);
    }

    public List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }

    public List<Shift> getShiftsForEmployee(Long employeeId) {
        User user = userRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return shiftRepository.findByAssignedUser(user);
    }

    public Shift assignShift(Long shiftId, Long employeeId) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        User user = userRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        shift.setAssignedUser(user);
        return shiftRepository.save(shift);
    }
}
