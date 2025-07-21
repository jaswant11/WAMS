@RestController
@RequestMapping("/fatigue")
public class FatigueController {

    @Autowired
    private FatigueService fatigueService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<Integer> getFatigueScore(@PathVariable Long employeeId) {
        int score = fatigueService.calculateFatigueScore(employeeId);
        return ResponseEntity.ok(score);
    }
}
