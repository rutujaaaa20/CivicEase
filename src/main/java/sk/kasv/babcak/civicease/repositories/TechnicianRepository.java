package sk.kasv.babcak.civicease.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sk.kasv.babcak.civicease.models.Technician;
import java.util.List;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {

    List<Technician> findByDepartmentId(Long departmentId);
    List<Technician> findByAvailableTrue();

    @Query("SELECT t FROM Technician t WHERE t.department.id = :departmentId AND t.available = true")
    List<Technician> findAvailableTechniciansByDepartment(@Param("departmentId") Long departmentId);
}
