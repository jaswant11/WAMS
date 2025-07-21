package com.wams.repository;

import com.wams.model.Employee;
import com.wams.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByEmployee(Employee employee);
}
