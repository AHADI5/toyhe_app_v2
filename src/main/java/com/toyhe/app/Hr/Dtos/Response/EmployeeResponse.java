package com.toyhe.app.Hr.Dtos.Response;

import com.toyhe.app.Hr.Models.Degree;


import java.util.List;
import java.util.stream.Collectors;

public record EmployeeResponse(
        String firstName,
        String lastName,
        String email,
        String phone,
        boolean isManger,
        String specialization,
        Degree degree,
        DepartmentResponse departmentResponse,
        PositionResponse positionResponse,
        List<EmployeeResponse> subordinates
) {
    // Static method to convert an Employee entity to EmployeeResponse DTO
    public static EmployeeResponse fromEntity(com.toyhe.app.Hr.Models.Employee employee) {
        if (employee == null) {
            return null;
        }

        return new EmployeeResponse(
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.isManager(),
                employee.getSpecialization(),
                employee.getDegree(),
                employee.getDepartment() != null ? DepartmentResponse.fromEntity(employee.getDepartment()) : null,
                employee.getPosition() != null ? PositionResponse.fromEntity(employee.getPosition()) : null,
                employee.getSubordinates() != null
                        ? employee.getSubordinates().stream()
                        .map(EmployeeResponse::fromEntity)
                        .collect(Collectors.toList())
                        : List.of()
        );
    }
}
