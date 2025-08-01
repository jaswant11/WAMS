package com.wams.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wams.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}