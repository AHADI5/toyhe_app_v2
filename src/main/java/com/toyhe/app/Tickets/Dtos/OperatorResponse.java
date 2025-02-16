package com.toyhe.app.Tickets.Dtos;

import com.toyhe.app.Customer.Models.Customer;
import com.toyhe.app.Customer.Repository.CustomerRepository;
import com.toyhe.app.Hr.Models.Employee;
import com.toyhe.app.Hr.Repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public record OperatorResponse(
        CustomerRepository customerRepository ,
        EmployeeRepository employeeRepository
) {
    public Operator fromEmail (String email){
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        Customer customer  = customerRepository.findCustomerByCustomerEmail(email);

        if (employee != null && "Treller".equalsIgnoreCase(employee.getPosition().getPositionName())) {
            return new Operator(
                    getFullName(employee.getFirstName(), employee.getLastName()),
                    employee.getEmail() ,
                    "Treller"
            ) ;
        } else if (employee != null) {
            return new Operator(
                    getFullName(employee.getFirstName(), employee.getLastName()),
                    employee.getEmail() ,
                    "Employee"
            ) ;
        } else {
            return new Operator(
                    customer.getCustomerName()  ,
                    customer.getCustomerEmail(),
                    "Customer"
            ) ;
        }
    }


    private String getFullName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }
}
