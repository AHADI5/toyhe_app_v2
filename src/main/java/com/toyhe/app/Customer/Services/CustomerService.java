package com.toyhe.app.Customer.Services;

import com.toyhe.app.Customer.Dtos.Requests.CustomerRegisterRequest;
import com.toyhe.app.Customer.Dtos.Response.CustomerResponse;
import com.toyhe.app.Customer.Models.Customer;
import com.toyhe.app.Customer.Repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record CustomerService(
        CustomerRepository  customerRepository

) {
    public ResponseEntity<CustomerResponse> createCustomer(CustomerRegisterRequest customerRegisterRequest) {
        Customer customer = Customer.builder()
                .customerName(customerRegisterRequest.customerName())
                .customerEmail(customerRegisterRequest.customerEmail())
                .customerAddress(customerRegisterRequest.customerAddress())
                .phoneNumber(customerRegisterRequest.phoneNumber())
                .build();
        customer = customerRepository.save(customer);

        return ResponseEntity.ok(CustomerResponse.fromEntity(customer));

    }

    public Customer getCustomerByCustomerEmail(String email) {
        return customerRepository.findCustomerByCustomerEmail(email);
    }

    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findCustomerByPhoneNumber(phoneNumber) ;
    }

    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<Customer> customers  = customerRepository.findAll();
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (Customer customer : customers) {
            customerResponses.add(CustomerResponse.fromEntity(customer));
        }
        return ResponseEntity.ok(customerResponses) ;
    }
}
