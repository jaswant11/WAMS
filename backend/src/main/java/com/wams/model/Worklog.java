@Entity
public class Worklog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;
    private Long shiftId;
    private String date;

    private double hoursWorked;

    // Getters and Setters
}
