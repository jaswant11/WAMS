package com.wams.service;

import com.wams.model.TimeOff;
import com.wams.repository.TimeOffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TimeOffService {

    @Autowired
    private TimeOffRepository timeOffRepository;

    public TimeOff requestTimeOff(TimeOff timeOff) {
        return timeOffRepository.save(timeOff);
    }

    public List<TimeOff> getRequestsByEmployee(Long employeeId) {
        return timeOffRepository.findByEmployeeId(employeeId);
    }
}
