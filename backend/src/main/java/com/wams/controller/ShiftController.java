@RestController
@RequestMapping("/shifts")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private WorklogService worklogService;

    // Manager creates a new shift
    @PostMapping
    public ResponseEntity<Shift> createShift(@RequestBody Shift shift) {
        return ResponseEntity.ok(shiftService.createShift(shift));
    }

    // View all shifts
    @GetMapping
    public List<Shift> getAllShifts() {
        return shiftService.getAllShifts();
    }

    // View all shifts for a specific employee
    @GetMapping("/employee/{id}")
    public List<Shift> getShiftsForEmployee(@PathVariable Long id) {
        return shiftService.getShiftsForEmployee(id);
    }

    // Assign a shift to an employee
    @PostMapping("/{shiftId}/assign/{employeeId}")
    public ResponseEntity<Shift> assignShift(
        @PathVariable Long shiftId,
        @PathVariable Long employeeId) {
        return ResponseEntity.ok(shiftService.assignShift(shiftId, employeeId));
    }

    // Mark a shift as completed and log to worklog
    @PutMapping("/{shiftId}/complete")
    public ResponseEntity<Shift> completeShift(@PathVariable Long shiftId) {
        Shift shift = shiftService.markShiftComplete(shiftId);
        worklogService.logCompletedShift(shift);
        return ResponseEntity.ok(shift);
    }
}
