package com.toyhe.app.Hr.Dtos.Requests;

import com.toyhe.app.Hr.Models.Degree;

public record EmployeeRequest(
        String firstName  ,
        String lastName  ,
        String email  ,
        String phone  ,
        String specialization ,
        Degree degree   ,
        long departmentID ,
        long positionID
) {
}
