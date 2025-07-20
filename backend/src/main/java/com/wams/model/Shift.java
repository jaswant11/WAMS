@Entity
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role; // e.g., cashier, loader, etc.

    private String date; // Format: YYYY-MM-DD

    private String startTime;
    private String endTime;

    private Long managerId;

    private Long assignedEmployeeId; // Nullable

    private String status; // OPEN, ASSIGNED, COMPLETED

    // Getters & Setters
}
