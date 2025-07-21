@RestController
@RequestMapping("/worklogs")
public class WorklogController {

    @Autowired
    private WorklogService worklogService;

    // View all worklogs (admin/manager use)
    @GetMapping
    public List<Worklog> getAll() {
        return worklogService.getAllWorklogs();
    }

    // View worklogs for a specific employee
    @GetMapping("/employee/{id}")
    public List<Worklog> getForEmployee(@PathVariable Long id) {
        return worklogService.getWorklogsForEmployee(id);
    }
}
