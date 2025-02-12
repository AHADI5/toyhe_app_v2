package com.toyhe.app.Auth.Dtos.Requests;

import com.toyhe.app.Auth.Model.Address;
import com.toyhe.app.Auth.Model.InUser;
import com.toyhe.app.Auth.Model.Role;

import java.util.List;

public record NewInUserRequest(
        String firstName ,
        Role role  ,
        String lastName ,
        Address address ,
        String email,
        String password,
        List<Long> rolesId  ,

        String phoneNumber,

        String dateOfBirth,
        String gender,
        String service
) {


}
