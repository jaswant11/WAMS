package com.wams.repository;
package com.wams.repository;

import com.wams.model.Employee;
import com.wams.model.TimeOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeOffRepository extends JpaRepository<TimeOff, Long> {
    List<TimeOff> findByEmployeeAndDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
    List<TimeOff> findByEmployeeId(Long employeeId);
}
