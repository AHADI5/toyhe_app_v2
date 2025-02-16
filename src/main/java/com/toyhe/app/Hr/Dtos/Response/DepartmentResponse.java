package com.toyhe.app.Hr.Dtos.Response;

import com.toyhe.app.Hr.Models.Department;

import java.util.List;
import java.util.stream.Collectors;

public record DepartmentResponse(
        long departmentID,
        String departmentName,
        List<EmployeeResponse> employeeResponseList,
        DepartmentResponse parentDepartment,
        List<DepartmentResponse> departmentChildren
) {
    // Static method to convert an entity to a DTO
    public static DepartmentResponse fromEntity(Department department) {
        if (department == null) {
            return null;
        }

        return new DepartmentResponse(
                department.getDepartmentId(),
                department.getDepartmentName(),
                department.getEmployees() != null
                        ? department.getEmployees().stream()
                        .map(EmployeeResponse::fromEntity)
                        .collect(Collectors.toList())
                        : List.of(),
                fromEntity(department.getParentDepartment()), // Convert parent recursively
                department.getDepartmentChildren() != null
                        ? department.getDepartmentChildren().stream()
                        .map(DepartmentResponse::fromEntity)
                        .collect(Collectors.toList())
                        : List.of()
        );
    }
}
