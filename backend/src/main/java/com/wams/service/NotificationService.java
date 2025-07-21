package com.wams.service;

import com.wams.model.Notification;
import com.wams.model.User;
import com.wams.repository.NotificationRepository;
import com.wams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public Notification sendNotification(Long userId, String message) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            Notification notification = new Notification();
            notification.setEmployee(userOpt.get());
            notification.setMessage(message);
            notification.setRead(false);
            notification.setTimestamp(LocalDateTime.now());
            return notificationRepository.save(notification);
        }
        throw new RuntimeException("User not found with ID: " + userId);
    }

    public List<Notification> getNotificationsForUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            return notificationRepository.findByEmployeeOrderByTimestampDesc(userOpt.get());
        }
        throw new RuntimeException("User not found");
    }

    public void markAllAsRead(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            List<Notification> notifications = notificationRepository.findByEmployeeAndReadFalse(userOpt.get());
            for (Notification n : notifications) {
                n.setRead(true);
            }
            notificationRepository.saveAll(notifications);
        }
    }
}
