package com.wams.repository;

import com.wams.model.Employee;
import com.wams.model.Worklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorklogRepository extends JpaRepository<Worklog, Long> {
    List<Worklog> findByEmployeeAndDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
}
