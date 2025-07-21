package com.wams.service;

import com.wams.model.Employee;
import com.wams.model.Notification;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    public Notification sendNotification(Long employeeId, Notification notification) {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow();
        notification.setEmployee(employee);
        return notificationRepo.save(notification);
    }

    public List<Notification> getNotificationsForEmployee(Long employeeId) {
        return notificationRepo.findByEmployeeId(employeeId);
    }

    public void clearUnreadNotifications(Long employeeId) {
        notificationRepo.deleteByEmployeeIdAndReadFalse(employeeId);
    }
}
