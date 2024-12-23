package com.toyhe.app.Auth.Dtos.Requests;

public record AccessRightsRequest(
        long modelID  ,
        String userName ,
        AccessOperation accessOperation

) {
}
