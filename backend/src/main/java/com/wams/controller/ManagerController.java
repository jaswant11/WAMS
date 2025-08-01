package com.wams.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
   
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wams.dto.FatigueRecommendationDTO;
import com.wams.dto.ManagerDetailDTO;
import com.wams.dto.StaffingRequirementDTO;
import com.wams.model.Availability;
import com.wams.model.Manager;
import com.wams.model.TimeOff;
import com.wams.repository.AvailabilityRepository;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.ManagerRepository;
import com.wams.repository.ShiftRepository;
import com.wams.repository.TimeOffRepository;
import com.wams.service.FatigueService;
import com.wams.service.ManagerService;
import com.wams.service.ShiftService;
import com.wams.service.StaffingRequirementService;


@RestController
@RequestMapping("/manager")
public class ManagerController {
@Autowired
private EmployeeRepository employeeRepository;
    @Autowired
    private ManagerService managerService;

    @Autowired
    private ManagerRepository managerRepository;
      @Autowired
    private StaffingRequirementService staffingRequirementService;

    @Autowired
    private FatigueService fatigueService;

    @Autowired
    private ShiftService shiftService;
@Autowired
private ShiftRepository shiftRepository;


    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private TimeOffRepository timeOffRepository;


    // ✅ Admin fetches all manager data joined with user
    @GetMapping("/details")
    public ResponseEntity<List<ManagerDetailDTO>> getAllManagerDetails() {
        return ResponseEntity.ok(managerService.getAllManagerDetails());
    }

    // ✅ Admin updates manager table based on role-specific fields
    @PutMapping("/update/{id}")
    public ResponseEntity<Manager> updateManager(@PathVariable Long id, @RequestBody Manager updated) {
        Optional<Manager> opt = managerRepository.findById(id);
        if (opt.isPresent()) {
            Manager mgr = opt.get();
            if (updated.getName() != null) mgr.setName(updated.getName());
            if (updated.getDepartment() != null) mgr.setDepartment(updated.getDepartment());
            return ResponseEntity.ok(managerRepository.save(mgr));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/availability")
public ResponseEntity<List<Availability>> viewAllAvailability() {
    return ResponseEntity.ok(availabilityRepository.findAll());
}

@GetMapping("/timeoff")
public ResponseEntity<List<TimeOff>> viewAllTimeOffRequests() {
    return ResponseEntity.ok(timeOffRepository.findAll());
}
 @PostMapping("/set")
public ResponseEntity<String> setStaffingRequirement(@RequestBody StaffingRequirementDTO dto) {
    LocalDate parsedDate = LocalDate.parse(dto.getDate());
    staffingRequirementService.setStaffingRequirement(parsedDate, dto.getShiftTemplateId(), dto.getRequiredEmployees());
    return ResponseEntity.ok("Staffing requirement saved successfully.");
}
    @GetMapping("/recommend-workers")
    public ResponseEntity<Map<String, Object>> recommendWorkers(
            @RequestParam String date,
            @RequestParam Long templateId) {

        LocalDate localDate = LocalDate.parse(date);

        // Compare staffing
        Map<String, Object> staffing = staffingRequirementService.evaluateStaffing(localDate, templateId);

        int assigned = (int) staffing.get("assigned");
        int required = (int) staffing.get("required");

        if (assigned >= required) {
            staffing.put("message", "Shift is already fully staffed.");
            staffing.put("recommendations", List.of());
            return ResponseEntity.ok(staffing);
        }

        // Get unassigned employees sorted by fatigue score
        List<FatigueRecommendationDTO> recommendations =
                fatigueService.getRecommendedEmployeesForShift(localDate, templateId);

        staffing.put("message", "Shift is understaffed. Showing recommended employees.");
        staffing.put("recommendations", recommendations);

        return ResponseEntity.ok(staffing);
    }
@PostMapping("/approve-timeoff")
public ResponseEntity<String> approveTimeOff(@RequestParam Long timeOffId) {
    TimeOff request = timeOffRepository.findById(timeOffId)
            .orElseThrow(() -> new RuntimeException("TimeOff not found"));

    request.setStatus("APPROVED");
    timeOffRepository.save(request);

    shiftService.removeEmployeeFromShiftsInDateRange(
        request.getEmployeeId(), request.getStartDate(), request.getEndDate()
    );

    return ResponseEntity.ok("Time off approved and shifts updated.");
}
@PutMapping("/deny-timeoff/{id}")
public ResponseEntity<String> denyTimeOffRequest(@PathVariable Long id) {
    Optional<TimeOff> optional = timeOffRepository.findById(id);
    if (optional.isEmpty()) return ResponseEntity.notFound().build();

    TimeOff request = optional.get();
    request.setStatus("DENIED");
    timeOffRepository.save(request);

    return ResponseEntity.ok("Time-off denied.");
}
 @DeleteMapping("/remove-employee-shifts")
public ResponseEntity<String> removeEmployeeFromShifts(
        @RequestParam Long employeeId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

    shiftService.removeEmployeeFromShiftsInDateRange(employeeId, startDate, endDate);
    return ResponseEntity.ok("Employee removed from assigned shifts.");
}
    
}
