package com.wams.repository;

import com.wams.model.Availability;
import com.wams.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByEmployee(Employee employee);
    List<Availability> findByEmployeeAndDate(Employee employee, LocalDate date);
    List<Availability> findByEmployeeAndDateBetween(Employee employee, LocalDate start, LocalDate end);
}
