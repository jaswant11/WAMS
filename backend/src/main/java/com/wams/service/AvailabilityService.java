package com.wams.service;

import com.wams.model.Availability;
import com.wams.repository.AvailabilityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {

    private final AvailabilityRepository repo;

    public AvailabilityService(AvailabilityRepository repo) {
        this.repo = repo;
    }

    public List<Availability> getAll() {
        return repo.findAll();
    }

    public void add(Availability availability) {
        repo.save(availability);
    }
}