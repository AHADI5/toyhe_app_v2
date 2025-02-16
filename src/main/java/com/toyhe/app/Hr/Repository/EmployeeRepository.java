package com.toyhe.app.Hr.Repository;

import com.toyhe.app.Hr.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeByEmail(String email);
}
