public interface WorklogRepository extends JpaRepository<Worklog, Long> {
    List<Worklog> findByEmployeeId(Long employeeId);
}