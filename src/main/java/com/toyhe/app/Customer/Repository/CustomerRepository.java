package com.toyhe.app.Customer.Repository;

import com.toyhe.app.Customer.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByUserAccountID(long userAccountID);
    Customer findCustomerByCustomerEmail(String customerEmail);
    Customer findCustomerByPhoneNumber(String phoneNumber);
}
