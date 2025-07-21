package com.wams.repository;

import com.wams.model.ShiftTemplate;
import com.wams.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftTemplateRepository extends JpaRepository<ShiftTemplate, Long> {
    List<ShiftTemplate> findByManager(User manager);
    List<ShiftTemplate> findByDayOfWeek(String dayOfWeek);
}
