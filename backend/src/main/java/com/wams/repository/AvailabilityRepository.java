package com.wams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wams.model.Availability;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByEmployeeId(Long employeeId);
}
