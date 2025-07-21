package com.wams.repository;

import com.wams.model.ShiftTemplate;
import com.wams.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShiftTemplateRepository extends JpaRepository<ShiftTemplate, Long> {
    List<ShiftTemplate> findByCreatedBy(Manager manager);
}
