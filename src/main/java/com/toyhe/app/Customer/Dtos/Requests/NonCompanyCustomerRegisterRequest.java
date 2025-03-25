package com.toyhe.app.Customer.Dtos.Requests;


import com.toyhe.app.Auth.Dtos.Requests.AddressRequest;
import com.toyhe.app.Customer.Models.CustomerType;

import java.util.Date;

public record NonCompanyCustomerRegisterRequest(
        String customerName,
        String customerEmail,
        String phoneNumber,
        AddressRequest addressRequest,
        CustomerType customerType,
        String nationality,
        Date dateOfBirth,
        String placeOfBirth ,
        boolean isCompany
) {}

