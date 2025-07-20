@RestController
@RequestMapping("/shifts")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @PostMapping
    public ResponseEntity<Shift> createShift(@RequestBody Shift shift) {
        return ResponseEntity.ok(shiftService.createShift(shift));
    }

    @GetMapping
    public List<Shift> getAllShifts() {
        return shiftService.getAllShifts();
    }

    @GetMapping("/employee/{id}")
    public List<Shift> getShiftsForEmployee(@PathVariable Long id) {
        return shiftService.getShiftsForEmployee(id);
    }

    @PostMapping("/{shiftId}/assign/{employeeId}")
    public ResponseEntity<Shift> assignShift(
        @PathVariable Long shiftId,
        @PathVariable Long employeeId) {
        return ResponseEntity.ok(shiftService.assignShift(shiftId, employeeId));
    }
}
