package com.wams.repository;
import com.wams.model.Employee;
import com.wams.model.TimeOff;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TimeOffRepository extends JpaRepository<TimeOff, Long> {
    List<TimeOff> findByEmployeeId(Long employeeId);
}
