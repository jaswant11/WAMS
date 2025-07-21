package com.wams.repository;

import com.wams.model.Availability;
import com.wams.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByEmployee(User employee);
    List<Availability> findByEmployeeAndDate(User employee, LocalDate date);
    List<Availability> findByEmployeeAndDateBetween(User employee, LocalDate start, LocalDate end);
}
