public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findByAssignedEmployeeId(Long employeeId);
    List<Shift> findByManagerId(Long managerId);
}