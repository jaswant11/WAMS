package com.wams.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wams.model.Shift;
import com.wams.model.ShiftTemplate;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findByDate(LocalDate date);
    List<Shift> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Shift> findByShiftTemplateAndDate(ShiftTemplate template, LocalDate date);
}
