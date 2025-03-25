package com.toyhe.app.Customer.Controllers;

import com.toyhe.app.Customer.Dtos.Requests.CompanyCustomerRegisterRequest;
import com.toyhe.app.Customer.Dtos.Requests.NonCompanyCustomerRegisterRequest;
import com.toyhe.app.Customer.Dtos.Response.CustomerResponse;
import com.toyhe.app.Customer.Models.CustomerType;
import com.toyhe.app.Customer.Models.NonCompanyCustomer;
import com.toyhe.app.Customer.Services.CustomerService;
import com.toyhe.app.Tickets.Dtos.OperatorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/av1/customers")
public record CustomerControllers(
        CustomerService customerService ,
        OperatorResponse  operatorResponse
) {
    @PostMapping("/companyCustomer")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CompanyCustomerRegisterRequest customerRegisterRequest) {
        return ResponseEntity.ok(CustomerResponse.fromEntity(customerService.createCompanyCustomer(customerRegisterRequest , CustomerType.POTENTIAL_CUSTOMER) ,operatorResponse));
    }

    @PostMapping("/customer")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody NonCompanyCustomerRegisterRequest customerRegisterRequest) {

        return ResponseEntity.ok(CustomerResponse.fromEntity(customerService.createNonCompanyCustomer(customerRegisterRequest  , CustomerType.COMPANY),operatorResponse));
    }
    @GetMapping("/")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return customerService.getAllCustomers () ;
    }
}
