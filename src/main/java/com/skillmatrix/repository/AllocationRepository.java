package com.skillmatrix.repository;

import com.skillmatrix.entity.Allocation;
import com.skillmatrix.entity.AllocationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {

    List<Allocation> findByEmployeeId(Long employeeId);

    List<Allocation> findByProjectId(Long projectId);

    List<Allocation> findByEmployeeIdAndStatus(Long employeeId, AllocationStatus status);

    List<Allocation> findByProjectIdAndStatus(Long projectId, AllocationStatus status);

    @Query("SELECT COALESCE(SUM(a.allocationPercentage), 0) FROM Allocation a WHERE a.employee.id = :employeeId AND a.status = 'ACTIVE'")
    Integer sumActiveAllocationPercentageByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT COALESCE(SUM(a.allocationPercentage), 0) FROM Allocation a WHERE a.employee.id = :employeeId AND a.status = 'ACTIVE' AND a.id != :excludeAllocationId")
    Integer sumActiveAllocationPercentageByEmployeeIdExcluding(@Param("employeeId") Long employeeId, @Param("excludeAllocationId") Long excludeAllocationId);
}
