package com.toyhe.app.Tickets.Dtos;

import com.toyhe.app.Customer.Dtos.Requests.CompanyCustomerRegisterRequest;
import com.toyhe.app.Customer.Dtos.Requests.NonCompanyCustomerRegisterRequest;

import java.util.List;

public record ReservationRequest(
        String customerId,
        CompanyCustomerRegisterRequest  companyCustomerRegisterRequest,
        NonCompanyCustomerRegisterRequest nonCompanyCustomerRegisterRequest,
        boolean isCompany ,
        String operator,
        long tripID  ,
        long classID ,
        String note ,
        List<GoodsRegisterRequest> goodsList
) {
}
