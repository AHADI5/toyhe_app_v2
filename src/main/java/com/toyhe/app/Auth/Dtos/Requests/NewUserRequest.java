package com.toyhe.app.Auth.Dtos.Requests;

import com.toyhe.app.Customer.Dtos.Requests.CompanyCustomerRegisterRequest;
import com.toyhe.app.Customer.Dtos.Requests.NonCompanyCustomerRegisterRequest;

import java.util.List;

public record NewUserRequest(
        String email,
        String password,
        List<Long> rolesId ,
        boolean isCompany ,
        NonCompanyCustomerRegisterRequest nonCompanyCustomerRegisterRequest  ,
        CompanyCustomerRegisterRequest  companyCustomerRegisterRequest
) {


}
