package com.wams.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wams.model.Employee;
import com.wams.model.Shift;
import com.wams.model.ShiftTemplate;
import com.wams.model.StaffingRequirement;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.ShiftRepository;
import com.wams.repository.ShiftTemplateRepository;
import com.wams.repository.StaffingRequirementRepository;

@Service
public class StaffingRequirementService {

    @Autowired
    private StaffingRequirementRepository staffingRequirementRepository;

    @Autowired
    private ShiftTemplateRepository shiftTemplateRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // ✅ Create or update a staffing requirement
    public void setStaffingRequirement(LocalDate date, Long templateId, int required) {
        ShiftTemplate template = shiftTemplateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        StaffingRequirement requirement = staffingRequirementRepository
                .findByShiftTemplateAndDate(template, date)
                .orElse(new StaffingRequirement());

        requirement.setDate(date);
        requirement.setShiftTemplate(template);
        requirement.setRequiredEmployees(required);

        // Attach the existing shift if it exists
        List<Shift> shifts = shiftRepository.findByShiftTemplateAndDate(template, date);
        if (!shifts.isEmpty()) {
            requirement.setShift(shifts.get(0));
        }

        staffingRequirementRepository.save(requirement);
    }

    // ✅ Return assigned vs required counts
    public Map<String, Object> evaluateStaffing(LocalDate date, Long templateId) {
        ShiftTemplate template = shiftTemplateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        Optional<StaffingRequirement> requirementOpt = staffingRequirementRepository
                .findByShiftTemplateAndDate(template, date);

        List<Shift> shifts = shiftRepository.findByShiftTemplateAndDate(template, date);

        int assigned = 0;
        if (!shifts.isEmpty()) {
            Shift shift = shifts.get(0);
            assigned = shift.getEmployees() != null ? shift.getEmployees().size() : 0;
        }

        int required = requirementOpt.map(StaffingRequirement::getRequiredEmployees).orElse(0);

        Map<String, Object> map = new HashMap<>();
        map.put("assigned", assigned);
        map.put("required", required);
        return map;
    }

    // ✅ Employee accepts open shift
    public void acceptOpenShift(Long employeeId, Long shiftId) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (shift.getEmployees() == null) {
           shift.setEmployees(new ArrayList<>());

        }

        if (!shift.getEmployees().contains(employee)) {
            shift.getEmployees().add(employee);
            shiftRepository.save(shift);
        }
    }

    // ✅ Employee accepts VTO (removal from shift)
    public void acceptVTO(Long employeeId, Long shiftId) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (shift.getEmployees() != null && shift.getEmployees().contains(employee)) {
            shift.getEmployees().remove(employee);
            shiftRepository.save(shift);
        }
    }
}
