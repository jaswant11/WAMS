@Service
public class ShiftService {
    @Autowired
    private ShiftRepository shiftRepo;

    public Shift createShift(Shift shift) {
        shift.setStatus("OPEN");
        return shiftRepo.save(shift);
    }

    public List<Shift> getAllShifts() {
        return shiftRepo.findAll();
    }

    public List<Shift> getShiftsForEmployee(Long employeeId) {
        return shiftRepo.findByAssignedEmployeeId(employeeId);
    }

    public Shift assignShift(Long shiftId, Long employeeId) {
        Shift shift = shiftRepo.findById(shiftId).orElseThrow();
        shift.setAssignedEmployeeId(employeeId);
        shift.setStatus("ASSIGNED");
        return shiftRepo.save(shift);
    }
}