@Service
public class FatigueService {

    @Autowired
    private ShiftRepository shiftRepository;

    public int calculateFatigueScore(Long employeeId) {
        List<Shift> recentShifts = shiftRepository.findByAssignedEmployeeId(employeeId);

        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);

        double totalHours = 0;

        for (Shift shift : recentShifts) {
            LocalDate shiftDate = LocalDate.parse(shift.getDate());
            if (!shiftDate.isBefore(sevenDaysAgo)) {
                LocalTime start = LocalTime.parse(shift.getStartTime());
                LocalTime end = LocalTime.parse(shift.getEndTime());
                Duration duration = Duration.between(start, end);
                totalHours += duration.toHours();
            }
        }

        double fatigueScore = (totalHours / 40.0) * 100;
        return (int) Math.min(fatigueScore, 100);
    }
}
