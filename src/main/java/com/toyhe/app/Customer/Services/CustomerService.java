package com.toyhe.app.Customer.Services;

import com.toyhe.app.Auth.Dtos.Requests.AddressRequest;
import com.toyhe.app.Auth.Model.Address;
import com.toyhe.app.Auth.Repositories.AddressRepository;
import com.toyhe.app.Customer.Dtos.Requests.CompanyCustomerRegisterRequest;
import com.toyhe.app.Customer.Dtos.Requests.NonCompanyCustomerRegisterRequest;
import com.toyhe.app.Customer.Dtos.Response.CustomerResponse;
import com.toyhe.app.Customer.Models.CompanyCustomer;
import com.toyhe.app.Customer.Models.Customer;
import com.toyhe.app.Customer.Models.CustomerType;
import com.toyhe.app.Customer.Models.NonCompanyCustomer;
import com.toyhe.app.Customer.Repository.CompanyCustomerRepository;
import com.toyhe.app.Customer.Repository.CustomerRepository;
import com.toyhe.app.Customer.Repository.NonCompanyRepository;
import com.toyhe.app.Tickets.Dtos.OperatorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public record CustomerService(
        NonCompanyRepository nonCompanyRepository ,
        CompanyCustomerRepository companyCustomerRepository ,
        OperatorResponse operatorResponse ,
        AddressRepository addressRepository ,
        CustomerRepository customerRepository

) {


    public Address saveAddress(AddressRequest addressRequest) {
        log.info("Saving address");
        Address address = Address.builder()
                .town(addressRequest.town())
                .avenue(addressRequest.avenue())
                .commune(addressRequest.commune())
                .province(addressRequest.province())
                .build();
         return addressRepository.save(address);
    }

    public Customer createCompanyCustomer(CompanyCustomerRegisterRequest request, CustomerType customerType) {
        log.info("Creating company customer");
        CompanyCustomer companyCustomer = CompanyCustomer.builder()
                .rccmNumber(request.rccmNumber())
                .creationYear(request.creationYear())
                .taxRessortNumber(request.taxRessortNumber())
                .nationalIdentificationNumber(request.taxIdentificationNumber())
                .address(request.addressRequest() != null ? saveAddress(request.addressRequest()) : null)
                .customerName(request.customerName())
                .customerEmail(request.customerEmail())
                .customerType(customerType)
                .phoneNumber(request.phoneNumber())
                .isCompany(true)
                .build();
        return companyCustomerRepository.save(companyCustomer);
    }

    public Customer createNonCompanyCustomer(NonCompanyCustomerRegisterRequest request, CustomerType customerType) {
        log.info("Creating non company customer");
        NonCompanyCustomer nonCompanyCustomer = NonCompanyCustomer.builder()
                .nationality(request.nationality())
                .dateOfBirth(request.dateOfBirth())
                .placeOfBirth(request.placeOfBirth())
                .address(request.addressRequest() != null ? saveAddress(request.addressRequest()) : null)
                .customerName(request.customerName())
                .customerEmail(request.customerEmail())
                .customerType(customerType)
                .phoneNumber(request.phoneNumber())
                .isCompany(false)
                .build();
        return nonCompanyRepository.save(nonCompanyCustomer);
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
            customerResponses.add(CustomerResponse.fromEntity(customer , operatorResponse));
        }
        return ResponseEntity.ok(customerResponses) ;
    }

    public boolean isDataBaseEmpty() {
        return customerRepository.count() == 0;
    }
}
