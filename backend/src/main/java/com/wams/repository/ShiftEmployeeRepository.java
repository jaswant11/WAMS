package com.wams.repository;

import com.wams.model.ShiftEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftEmployeeRepository extends JpaRepository<ShiftEmployee, Long> {
}
