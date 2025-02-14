package com.toyhe.app.Customer.Controllers;

import com.toyhe.app.Customer.Dtos.Requests.CustomerRegisterRequest;
import com.toyhe.app.Customer.Dtos.Response.CustomerResponse;
import com.toyhe.app.Customer.Services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/av1/customers")
public record CustomerControllers(
        CustomerService customerService
) {
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return customerService.getAllCustomers () ;
    }

    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRegisterRequest customerRegisterRequest) {

       return customerService.createCustomer (customerRegisterRequest);
    }
}
