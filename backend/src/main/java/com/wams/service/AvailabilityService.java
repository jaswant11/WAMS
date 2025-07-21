package com.wams.service;

import com.wams.model.Availability;
import com.wams.model.Employee;
import com.wams.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepo;

    public Availability saveAvailability(Availability availability) {
        return availabilityRepo.save(availability);
    }

    public List<Availability> getAvailabilityForEmployee(Long employeeId) {
        return availabilityRepo.findByEmployeeId(employeeId);
    }

    public boolean isEmployeeAvailable(Employee employee, LocalDate date, String startTime, String endTime) {
        List<Availability> availableSlots = availabilityRepo.findByEmployeeAndDate(employee, date);
        for (Availability slot : availableSlots) {
            if (slot.getStartTime().compareTo(startTime) <= 0 &&
                slot.getEndTime().compareTo(endTime) >= 0) {
                return true;
            }
        }
        return false;
    }
}
