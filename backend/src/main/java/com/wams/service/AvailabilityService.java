package com.wams.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wams.model.Availability;
import com.wams.repository.AvailabilityRepository;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    public Availability submitAvailability(Availability availability) {
        return availabilityRepository.save(availability);
    }

    public List<Availability> getAvailabilityByEmployee(Long employeeId) {
        return availabilityRepository.findByEmployeeId(employeeId);
    }
}
