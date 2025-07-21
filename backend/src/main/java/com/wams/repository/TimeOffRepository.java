package com.wams.repository;

import com.wams.model.TimeOff;
import com.wams.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeOffRepository extends JpaRepository<TimeOff, Long> {
    List<TimeOff> findByEmployee(User employee);
    List<TimeOff> findByEmployeeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(User employee, LocalDate start, LocalDate end);
}
