package com.skillmatrix.repository;

import com.skillmatrix.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmployeeCode(String employeeCode);

    Optional<Employee> findByEmail(String email);

    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByEmail(String email);

    List<Employee> findByActiveTrue();

    @Query("SELECT DISTINCT e FROM Employee e LEFT JOIN FETCH e.employeeSkills es LEFT JOIN FETCH es.skill WHERE e.active = true")
    List<Employee> findAllActiveWithSkills();
}
