package com.wams.repository;

import com.wams.model.Shift;
import com.wams.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findByAssignedUser(User user);
    List<Shift> findByDate(LocalDate date);
    List<Shift> findByAssignedUserAndDate(User user, LocalDate date);
}
