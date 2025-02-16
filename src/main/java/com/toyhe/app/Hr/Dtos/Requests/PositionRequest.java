package com.toyhe.app.Hr.Dtos.Requests;

public record PositionRequest(
        String positionName ,
        String description  ,
        long departmentID
) {
}
