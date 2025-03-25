package com.toyhe.app.Customer.Dtos.Requests;


import com.toyhe.app.Auth.Dtos.Requests.AddressRequest;
import com.toyhe.app.Customer.Models.CustomerType;

public record CompanyCustomerRegisterRequest(
        String customerName,
        String customerEmail,
        String phoneNumber,
        CustomerType customerType,
        AddressRequest addressRequest,
        int taxIdentificationNumber,
        int rccmNumber  ,
        int creationYear ,
        int taxRessortNumber ,
        boolean isCompany


) {

}
