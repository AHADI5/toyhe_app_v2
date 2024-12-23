package com.toyhe.app.Auth.Dtos.Requests;

public record AccessOperation(
        int read ,
        int write ,
        int update ,
        int delete
) {
}
