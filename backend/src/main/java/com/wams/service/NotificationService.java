@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Create a new notification
    public Notification createNotification(Long recipientId, String message, String type) {
        Notification notification = new Notification();
        notification.setRecipientId(recipientId);
        notification.setMessage(message);
        notification.setType(type);
        notification.setTimestamp(LocalDateTime.now());
        notification.setIsRead(false);
        return notificationRepository.save(notification);
    }

    // Get all notifications for a user
    public List<Notification> getNotifications(Long userId) {
        return notificationRepository.findByRecipientId(userId);
    }

    // Mark notification as read
    public Notification markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        return notificationRepository.save(notification);
    }

    // Delete a notification
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
