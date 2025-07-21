package com.wams.repository;

import com.wams.model.Employee;
import com.wams.model.TimeOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeOffRepository extends JpaRepository<TimeOff, Long> {
    List<TimeOff> findByEmployee(Employee employee);
    List<TimeOff> findByEmployeeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Employee employee, LocalDate date1, LocalDate date2);
}
