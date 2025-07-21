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

    @Autowired
    private ShiftRepository shiftRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private AvailabilityRepository availabilityRepo;

    @Autowired
    private NotificationRepository notificationRepo;

    @Autowired
    private FatigueService fatigueService; // Optional – if ML is plugged in

    public Shift createShift(Shift shift) {
        return shiftRepo.save(shift);
    }

    public List<Shift> getAllShifts() {
        return shiftRepo.findAll();
    }

    public List<Shift> getShiftsForEmployee(Long employeeId) {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow();
        return shiftRepo.findByEmployee(employee);
    }

    public List<Shift> getShiftsByDate(LocalDate date) {
        return shiftRepo.findByDate(date);
    }

    public Shift assignShift(Long shiftId, Long employeeId) {
        Shift shift = shiftRepo.findById(shiftId).orElseThrow();
        Employee employee = employeeRepo.findById(employeeId).orElseThrow();
        shift.setEmployee(employee);
        return shiftRepo.save(shift);
    }

    public String handleStaffingRequirement(LocalDate date, int requiredEmployees) {
        List<Shift> shifts = shiftRepo.findByDate(date);
        int currentlyAssigned = (int) shifts.stream().filter(s -> s.getEmployee() != null).count();

        if (currentlyAssigned == requiredEmployees) {
            return "Staffing is already satisfied.";
        }

        if (currentlyAssigned > requiredEmployees) {
            int excess = currentlyAssigned - requiredEmployees;
            // Assign VTOs
            assignVTOs(shifts, excess);
            return "VTO offered to " + excess + " employees.";
        } else {
            int shortage = requiredEmployees - currentlyAssigned;
            return openVoluntaryShifts(date, shortage);
        }
    }

    private void assignVTOs(List<Shift> shifts, int vtoCount) {
        List<Shift> assignedShifts = shifts.stream()
                .filter(s -> s.getEmployee() != null)
                .sorted(Comparator.comparing(s -> fatigueService.getFatigueScore(s.getEmployee().getId())))
                .collect(Collectors.toList());

        for (int i = 0; i < vtoCount && i < assignedShifts.size(); i++) {
            Shift shift = assignedShifts.get(i);
            sendNotification(shift.getEmployee(), "VTO available for shift on " + shift.getDate());
        }
    }

    private String openVoluntaryShifts(LocalDate date, int shortage) {
        List<Availability> available = availabilityRepo.findByDate(date);
        List<Employee> interested = new ArrayList<>();

        for (Availability a : available) {
            int fatigueScore = fatigueService.getFatigueScore(a.getEmployee().getId());
            if (fatigueScore < 80) {
                interested.add(a.getEmployee());
            }
        }

        int invited = 0;
        for (Employee e : interested) {
            sendNotification(e, "Shift available on " + date + ". Click to accept or ignore.");
            invited++;
            if (invited >= shortage) break;
        }

        return "Invited " + invited + " employees to fill " + shortage + " shifts.";
    }

    private void sendNotification(Employee employee, String message) {
        Notification note = new Notification();
        note.setEmployee(employee);
        note.setMessage(message);
        note.setRead(false);
        notificationRepo.save(note);
    }
}
