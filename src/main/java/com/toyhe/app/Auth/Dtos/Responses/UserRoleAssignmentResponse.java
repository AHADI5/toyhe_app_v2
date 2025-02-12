package com.toyhe.app.Auth.Dtos.Responses;

import java.util.List;

public record UserRoleAssignmentResponse(
        UserResponse userResponse  ,
        List<UserRoleResponse> userRoleResponseList
        ) {


}
