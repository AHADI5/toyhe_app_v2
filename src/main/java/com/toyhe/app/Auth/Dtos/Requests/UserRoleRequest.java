package com.toyhe.app.Auth.Dtos.Requests;

public record UserRoleRequest(
        String roleName,
        String description
) {
}
