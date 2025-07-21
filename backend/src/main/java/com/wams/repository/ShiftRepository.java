package com.wams.repository;

import com.wams.model.Shift;
import com.wams.model.Employee;
import com.wams.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long> {

    List<Shift> findByAssignedEmployee(Employee employee);

    List<Shift> findByCreatedBy(Manager manager);

    List<Shift> findByStatus(String status);
}
