package com.wams.repository;

import com.wams.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<User, Long> {
    List<User> findByRole(String role);
}
