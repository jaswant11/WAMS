public interface TimeOffRepository extends JpaRepository<TimeOff, Long> {
    List<TimeOff> findByEmployee(Employee employee);
}
