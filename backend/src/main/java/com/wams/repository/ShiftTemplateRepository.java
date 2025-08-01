package com.wams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wams.model.ShiftTemplate;

@Repository
public interface ShiftTemplateRepository extends JpaRepository<ShiftTemplate, Long> {
  List<ShiftTemplate> findByEmployees_Id(Long id);

    
}