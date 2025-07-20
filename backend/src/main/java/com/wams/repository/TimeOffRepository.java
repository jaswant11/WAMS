package com.wams.repository;

import com.wams.model.TimeOff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeOffRepository extends JpaRepository<TimeOff, Long> {
}
