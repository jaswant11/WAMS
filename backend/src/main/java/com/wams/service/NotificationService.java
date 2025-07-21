package com.wams.service;

import com.wams.model.Employee;
import com.wams.model.Notification;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    public Notification createNotification(Long employeeId, String message) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Notification notification = new Notification();
        notification.setEmployee(employee);
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRead(false);

        return notificationRepo.save(notification);
    }

    public List<Notification> getNotificationsForEmployee(Long employeeId) {
        return notificationRepo.findByEmployeeIdOrderByTimestampDesc(employeeId);
    }

    public void markAllAsRead(Long employeeId) {
        List<Notification> unread = notificationRepo.findByEmployeeIdAndReadFalse(employeeId);
        for (Notification n : unread) {
            n.setRead(true);
        }
        notificationRepo.saveAll(unread);
    }
}
