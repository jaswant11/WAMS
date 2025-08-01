package com.wams.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wams.model.Shift;
import com.wams.model.ShiftTemplate;
import com.wams.model.StaffingRequirement;

@Repository
public interface StaffingRequirementRepository extends JpaRepository<StaffingRequirement, Long> {
    Optional<StaffingRequirement> findByDateAndShiftTemplate(LocalDate date, ShiftTemplate template);
    Optional<StaffingRequirement> findByShift(Shift shift);
    Optional<StaffingRequirement> findByShiftTemplateAndDate(ShiftTemplate template, LocalDate date);

}
