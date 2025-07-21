package com.wams.repository;

import com.wams.model.Shift;
import com.wams.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findByEmployee(Employee employee);
    List<Shift> findByDate(LocalDate date);
    List<Shift> findByDateAndEmployee(LocalDate date, Employee employee);
}
