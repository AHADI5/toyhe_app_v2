package com.toyhe.app.Auth.Dtos.Requests;


import com.toyhe.app.Auth.Model.Address;

import java.util.List;

public record NewExternalUserRequest(
        String firstName ,
        String lastName ,
        String email,
        String password,
        Address address ,
        List<AccessRightsRequest> authorities ,

        String phoneNumber,

        String dateOfBirth,
        String gender
) {


}

