package com.wams.service;

import com.wams.model.TimeOff;
import com.wams.repository.TimeOffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeOffService {

    @Autowired
    private TimeOffRepository repository;

    public List<TimeOff> getAllRequests() {
        return repository.findAll();
    }

    public void requestTimeOff(TimeOff request) {
        repository.save(request);
    }
}