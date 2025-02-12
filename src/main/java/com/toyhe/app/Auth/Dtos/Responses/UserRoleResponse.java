package com.toyhe.app.Auth.Dtos.Responses;



import java.util.List;

public record UserRoleResponse(
        String name  ,
        String description ,
        List<UserResponse> userResponseList

) {
}
