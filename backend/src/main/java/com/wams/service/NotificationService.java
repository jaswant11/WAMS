package com.wams.service;

import com.wams.model.Notification;
import com.wams.model.Employee;
import com.wams.repository.NotificationRepository;
import com.wams.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Notification sendNotification(Long employeeId, String message) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isPresent()) {
            Notification notification = new Notification();
            notification.setEmployee(employeeOpt.get());
            notification.setMessage(message);
            notification.setRead(false);
            notification.setTimestamp(java.time.LocalDateTime.now());
            return notificationRepository.save(notification);
        }
        throw new RuntimeException("Employee not found with ID: " + employeeId);
    }

    public List<Notification> getNotificationsForEmployee(Long employeeId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isPresent()) {
            return notificationRepository.findByEmployeeOrderByTimestampDesc(employeeOpt.get());
        }
        throw new RuntimeException("Employee not found with ID: " + employeeId);
    }

    public void markAllAsRead(Long employeeId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isPresent()) {
            List<Notification> notifications = notificationRepository.findByEmployeeAndReadFalse(employeeOpt.get());
            for (Notification n : notifications) {
                n.setRead(true);
            }
            notificationRepository.saveAll(notifications);
        }
    }
}
