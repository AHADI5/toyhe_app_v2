package com.toyhe.app.Hr.Dtos.Requests;

import java.util.List;

public record DepartmentRegisterRequest(
        String departmentName ,
        //An Employee
        long managerID ,
        long parentDepartmentID  ,
        List<Long> childrenDepartmentIDs
) {
}
