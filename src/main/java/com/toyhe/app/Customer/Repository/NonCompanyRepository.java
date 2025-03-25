package com.toyhe.app.Customer.Repository;

import com.toyhe.app.Customer.Models.Customer;
import com.toyhe.app.Customer.Models.NonCompanyCustomer;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NonCompanyRepository extends JpaRepository<NonCompanyCustomer, Long> {
}
