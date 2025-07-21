package com.wams.service;

import com.wams.model.*;
import com.wams.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShiftService {

    @Autowired private ShiftRepository shiftRepo;
    @Autowired private EmployeeRepository employeeRepo;
    @Autowired private ManagerRepository managerRepo;
    @Autowired private WorklogService worklogService;
    @Autowired private NotificationService notificationService;
    @Autowired private AvailabilityService availabilityService;
    @Autowired private FatigueService fatigueService;

    public Shift createShift(Long managerId, Shift shift) {
        Manager manager = managerRepo.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        shift.setCreatedBy(manager);
        shift.setStatus("OPEN");
        shift.setCurrentAssigned(0);

        Shift savedShift = shiftRepo.save(shift);

        recommendAndAssignEmployees(savedShift);  // 🔄 Recommendation triggered here

        return savedShift;
    }

    public Shift assignShift(Long shiftId, Long employeeId) {
        Shift shift = shiftRepo.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        boolean available = availabilityService.isEmployeeAvailable(employee, shift.getDate(), shift.getStartTime(), shift.getEndTime());
        if (!available) {
            throw new RuntimeException("Employee is not available during this shift.");
        }

        shift.setAssignedEmployee(employee);
        shift.setCurrentAssigned(shift.getCurrentAssigned() + 1);

        if (shift.getCurrentAssigned() >= shift.getRequiredEmployees()) {
            shift.setStatus("ASSIGNED");
            shift.setOpenForVolunteers(false);
        }

        Shift updatedShift = shiftRepo.save(shift);

        notificationService.createNotification(
                employee.getId(),
                "You have been assigned a shift on " + shift.getDate()
        );

        return updatedShift;
    }

    public void recommendAndAssignEmployees(Shift shift) {
        List<Employee> candidates = employeeRepo.findAll();
        Map<Employee, Double> fatigueScores = new HashMap<>();

        for (Employee emp : candidates) {
            boolean available = availabilityService.isEmployeeAvailable(emp, shift.getDate(), shift.getStartTime(), shift.getEndTime());
            if (available) {
                double fatigue = fatigueService.getNumericFatigueScore(emp.getId());
                fatigueScores.put(emp, fatigue);
            }
        }

        List<Employee> sortedCandidates = fatigueScores.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        int assignedCount = 0;
        for (Employee emp : sortedCandidates) {
            if (assignedCount >= shift.getRequiredEmployees()) break;

            shift.setAssignedEmployee(emp);
            shift.setCurrentAssigned(++assignedCount);
            notificationService.createNotification(emp.getId(), "You’ve been assigned a shift on " + shift.getDate());
        }

        if (assignedCount < shift.getRequiredEmployees()) {
            shift.setOpenForVolunteers(true);
            for (Employee emp : sortedCandidates) {
                notificationService.createNotification(emp.getId(),
                        "Volunteer shift available for " + shift.getDate() + " (" + shift.getRoleTitle() + ")");
            }
        }

        shiftRepo.save(shift);
    }

    public Shift respondToVoluntaryShift(Long shiftId, Long employeeId, String response) {
        Shift shift = shiftRepo.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!shift.isOpenForVolunteers()) {
            throw new RuntimeException("This shift is not open for volunteers.");
        }

        if ("accept".equalsIgnoreCase(response)) {
            shift.setAssignedEmployee(employee);
            shift.setCurrentAssigned(shift.getCurrentAssigned() + 1);

            if (shift.getCurrentAssigned() >= shift.getRequiredEmployees()) {
                shift.setOpenForVolunteers(false);
                shift.setStatus("ASSIGNED");
            }

            shiftRepo.save(shift);
            notificationService.createNotification(employeeId, "You accepted a volunteer shift on " + shift.getDate());

        } else if ("ignore".equalsIgnoreCase(response)) {
            notificationService.createNotification(employeeId, "You ignored the volunteer shift on " + shift.getDate());
        } else {
            throw new RuntimeException("Invalid response. Use 'accept' or 'ignore'.");
        }

        return shift;
    }

    public Shift completeShift(Long shiftId) {
        Shift shift = shiftRepo.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        shift.setStatus("COMPLETED");
        shiftRepo.save(shift);
        worklogService.logShiftCompletion(shift);
        return shift;
    }

    public List<Shift> getAllShifts() {
        return shiftRepo.findAll();
    }

    public List<Shift> getShiftsForEmployee(Long employeeId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return shiftRepo.findByAssignedEmployee(employee);
    }

    public List<Shift> getShiftsByManager(Long managerId) {
        Manager manager = managerRepo.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        return shiftRepo.findByCreatedBy(manager);
    }
}

