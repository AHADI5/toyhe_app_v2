package com.toyhe.app.Auth.Dtos.Requests;


import java.util.List;

public record NewExternalUserRequest(
        String firstName ,
        String lastName ,
        String email,
        String password,
        List<Long> rolesId  ,

        String phoneNumber,

        String dateOfBirth,
        String gender
) {


}

