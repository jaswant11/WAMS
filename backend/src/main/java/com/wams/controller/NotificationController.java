package com.wams.controller;

import com.wams.model.Notification;
import com.wams.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // 🔹 Get all notifications for an employee
    @GetMapping("/employee/{id}")
    public List<Notification> getEmployeeNotifications(@PathVariable Long id) {
        return notificationService.getNotificationsForEmployee(id);
    }

    // 🔹 Mark all as read
    @PostMapping("/employee/{id}/mark-read")
    public String markNotificationsAsRead(@PathVariable Long id) {
        notificationService.markAllAsRead(id);
        return "All notifications marked as read.";
    }
}
