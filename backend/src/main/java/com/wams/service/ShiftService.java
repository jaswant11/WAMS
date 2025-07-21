@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    // Create a new shift
    public Shift createShift(Shift shift) {
        shift.setStatus("OPEN");
        return shiftRepository.save(shift);
    }

    // Get all shifts
    public List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }

    // Get all shifts assigned to a specific employee
    public List<Shift> getShiftsForEmployee(Long employeeId) {
        return shiftRepository.findByAssignedEmployeeId(employeeId);
    }

    // Assign a shift to an employee
    public Shift assignShift(Long shiftId, Long employeeId) {
        Shift shift = shiftRepository.findById(shiftId)
            .orElseThrow(() -> new RuntimeException("Shift not found"));
        
        shift.setAssignedEmployeeId(employeeId);
        shift.setStatus("ASSIGNED");
        return shiftRepository.save(shift);
    }

    // Mark a shift as completed
    public Shift markShiftComplete(Long shiftId) {
        Shift shift = shiftRepository.findById(shiftId)
            .orElseThrow(() -> new RuntimeException("Shift not found"));

        shift.setStatus("COMPLETED");
        return shiftRepository.save(shift);
    }
}
