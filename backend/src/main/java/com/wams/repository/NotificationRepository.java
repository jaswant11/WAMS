package com.wams.repository;

import com.wams.model.Notification;
import com.wams.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByEmployee(User employee);
    List<Notification> findByEmployeeOrderByTimestampDesc(User employee);
    List<Notification> findByEmployeeAndReadFalse(User employee);
}
