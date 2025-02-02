package com.toyhe.app.Auth.Dtos.Requests;

import java.util.List;

public record UserRoleAssignementRequest(
        String userName  ,
        List<Long> userRoleIds
) {
}
