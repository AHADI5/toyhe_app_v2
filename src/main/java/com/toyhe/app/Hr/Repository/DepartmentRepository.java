package com.toyhe.app.Hr.Repository;

import com.toyhe.app.Hr.Models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
