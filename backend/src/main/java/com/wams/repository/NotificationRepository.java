package com.wams.repository;

import com.wams.model.Notification;
import com.wams.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByEmployee(Employee employee);
    List<Notification> findByEmployeeOrderByTimestampDesc(Employee employee);
    List<Notification> findByEmployeeAndReadFalse(Employee employee);
}
