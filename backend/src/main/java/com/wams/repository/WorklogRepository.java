package com.wams.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wams.model.Worklog;

public interface WorklogRepository extends JpaRepository<Worklog, Long> {
    List<Worklog> findByEmployeeIdAndDateBetween(Long employeeId, LocalDate start, LocalDate end);
    List<Worklog> findByEmployeeId(Long employeeId);
}
