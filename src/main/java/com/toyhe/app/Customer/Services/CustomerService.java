package com.toyhe.app.Customer.Services;

import com.toyhe.app.Customer.Dtos.Requests.CustomerRegisterRequest;
import com.toyhe.app.Customer.Dtos.Response.CustomerResponse;
import com.toyhe.app.Customer.Models.Customer;
import com.toyhe.app.Customer.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(
        CustomerRepository  customerRepository

) {
    public CustomerResponse createCustomer(CustomerRegisterRequest customerRegisterRequest) {
        Customer customer = Customer.builder()
                .customerName(customerRegisterRequest.customerName())
                .customerEmail(customerRegisterRequest.customerEmail())
                .phoneNumber(customerRegisterRequest.phoneNumber())
                .build();
        customer = customerRepository.save(customer);

        return CustomerResponse.fromEntity(customer);

    }

    public Customer getCustomerByCustomerEmail(String email) {
        return customerRepository.findCustomerByCustomerEmail(email);
    }

    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findCustomerByPhoneNumber(phoneNumber) ;
    }

    public Customer getCustomerByUserAccountId(long id) {
        return customerRepository.findByUserAccountID(id) ;
    }

}
