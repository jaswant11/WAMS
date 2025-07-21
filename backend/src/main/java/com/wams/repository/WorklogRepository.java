package com.wams.repository;

import com.wams.model.Worklog;
import com.wams.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorklogRepository extends JpaRepository<Worklog, Long> {
    List<Worklog> findByEmployee(User employee);
    List<Worklog> findByEmployeeAndDateBetween(User employee, LocalDate start, LocalDate end);
}
