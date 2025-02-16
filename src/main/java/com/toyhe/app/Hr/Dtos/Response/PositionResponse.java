package com.toyhe.app.Hr.Dtos.Response;


import com.toyhe.app.Hr.Models.Position;

public record PositionResponse(
        long positionID,
        String positionName,
        String description,
        EmployeeResponse employeeResponse,
        DepartmentResponse departmentResponse
) {
    // Static method to convert a Position entity to PositionResponse DTO
    public static PositionResponse fromEntity(Position position) {
        if (position == null) {
            return null;
        }

        return new PositionResponse(
                position.getPositionID(),
                position.getPositionName(),
                position.getDescription(),
                position.getEmployee() != null ? EmployeeResponse.fromEntity(position.getEmployee()) : null,
                position.getDepartment() != null ? DepartmentResponse.fromEntity(position.getDepartment()) : null
        );
    }
}
