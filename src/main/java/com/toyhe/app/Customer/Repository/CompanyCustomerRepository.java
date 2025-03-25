package com.toyhe.app.Customer.Repository;

import com.toyhe.app.Customer.Models.CompanyCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyCustomerRepository extends JpaRepository<CompanyCustomer,Long> {
}
