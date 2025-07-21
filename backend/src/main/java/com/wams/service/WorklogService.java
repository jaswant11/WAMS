@Service
public class WorklogService {

    @Autowired
    private WorklogRepository worklogRepository;

    public Worklog logCompletedShift(Shift shift) {
        if (shift.getAssignedEmployeeId() == null) {
            throw new IllegalArgumentException("Shift has no assigned employee");
        }

        Worklog worklog = new Worklog();
        worklog.setEmployeeId(shift.getAssignedEmployeeId());
        worklog.setShiftId(shift.getId());
        worklog.setDate(shift.getDate());

        // Calculate hours worked
        LocalTime start = LocalTime.parse(shift.getStartTime());
        LocalTime end = LocalTime.parse(shift.getEndTime());
        double hoursWorked = Duration.between(start, end).toMinutes() / 60.0;
        worklog.setHoursWorked(hoursWorked);

        return worklogRepository.save(worklog);
    }

    public List<Worklog> getWorklogsForEmployee(Long employeeId) {
        return worklogRepository.findByEmployeeId(employeeId);
    }

    public List<Worklog> getAllWorklogs() {
        return worklogRepository.findAll();
    }
}
