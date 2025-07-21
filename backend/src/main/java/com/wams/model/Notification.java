@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long recipientId;
    private String message;
    private String type; // e.g. SHIFT_ASSIGNED, TIME_OFF_STATUS
    private LocalDateTime timestamp;
    private boolean isRead;
}
