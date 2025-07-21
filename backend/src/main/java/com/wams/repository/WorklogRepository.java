package com.wams.repository;

import com.wams.model.Worklog;
import com.wams.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorklogRepository extends JpaRepository<Worklog, Long> {
    List<Worklog> findByEmployee(Employee employee);
}
