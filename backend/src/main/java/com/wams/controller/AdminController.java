package com.wams.controller;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.wams.dto.AssignShiftTemplateDTO;
import com.wams.dto.EmployeeDetailDTO;
import com.wams.dto.FatigueDTO;
import com.wams.dto.FatigueRecommendationDTO;
import com.wams.dto.ManagerDetailDTO;
import com.wams.dto.ShiftTemplateDTO;
import com.wams.model.ShiftTemplate;
import com.wams.model.User;
import com.wams.repository.ShiftTemplateRepository;
import com.wams.service.EmployeeService;
import com.wams.service.FatigueService;
import com.wams.service.ManagerService;
import com.wams.service.ShiftTemplateService;
import com.wams.service.UserService;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ManagerService managerService;
    @Autowired
private FatigueService fatigueService;
@Autowired
private ShiftTemplateService shiftTemplateService;
@Autowired
private ShiftTemplateRepository shiftTemplateRepository;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

@GetMapping("/fatigue/{employeeId}")
public ResponseEntity<FatigueDTO> getEmployeeFatigueById(@PathVariable Long employeeId) {
    FatigueDTO dto = fatigueService.calculateFatigue(employeeId);
    return ResponseEntity.ok(dto);
}
    // ✅ VIEW ALL USERS
    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    // ✅ UPDATE USER
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updated) {
        return ResponseEntity.ok(userService.updateUser(id, updated));
    }

    // ✅ DELETE USER
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    // ✅ VIEW EMPLOYEES (DETAILED)
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDetailDTO>> getAllEmployeesWithDetails() {
        return ResponseEntity.ok(employeeService.getAllEmployeeDetails());
    }

    // ✅ VIEW MANAGERS (DETAILED)
    @GetMapping("/managers")
    public ResponseEntity<List<ManagerDetailDTO>> getAllManagersWithDetails() {
        return ResponseEntity.ok(managerService.getAllManagerDetails());
    }

    // ✅ FILTER EMPLOYEES BY AVAILABILITY AND DEPARTMENT
    @GetMapping("/employees/filter")
    public ResponseEntity<List<EmployeeDetailDTO>> filterEmployees(
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) String department) {
        return ResponseEntity.ok(employeeService.filterByAvailabilityAndDepartment(available, department));
    }
     @PostMapping("/create-shift-template")
    public ResponseEntity<ShiftTemplate> createTemplate(@RequestBody ShiftTemplateDTO dto) {
        ShiftTemplate created = shiftTemplateService.createShiftTemplate(dto);
        return ResponseEntity.ok(created);
    }
    @PostMapping("/assign")
public ResponseEntity<String> assignTemplateToEmployees(@RequestBody AssignShiftTemplateDTO dto) {
    shiftTemplateService.assignTemplateToEmployees(
        dto.getTemplateId(),
        dto.getEmployeeIds(),
        LocalDate.parse(dto.getStartDate()),
        LocalDate.parse(dto.getEndDate())
    );
    return ResponseEntity.ok("Shift template assigned and shifts generated.");
}
@GetMapping("/recommendation")
public List<FatigueRecommendationDTO> getRecommendations() {
    return fatigueService.getRecommendations();
}



}
