package com.toyhe.app.Auth.Dtos.Requests;

public record AccessRightsRequest(
        long modelID ,
        long roleID ,
        AccessOperation accessOperation
) {
}
