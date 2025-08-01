package com.wams.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wams.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Employee findByUserUsername(String username);
}


