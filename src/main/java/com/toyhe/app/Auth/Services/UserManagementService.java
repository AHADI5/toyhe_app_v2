package com.toyhe.app.Auth.Services;

import com.toyhe.app.Auth.Dtos.Requests.NewUserRequest;
import com.toyhe.app.Auth.Dtos.Requests.UserRoleAssignementRequest;
import com.toyhe.app.Auth.Dtos.Responses.NewAccountResponse;
import com.toyhe.app.Auth.Model.*;
import com.toyhe.app.Auth.Repositories.UserRepository;
import com.toyhe.app.Customer.Dtos.Requests.CompanyCustomerRegisterRequest;
import com.toyhe.app.Customer.Models.CustomerType;
import com.toyhe.app.Customer.Services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserManagementService {
    final UserRepository userRepository;
    private final PasswordEncoder passWordEncoder;
    final UserService userService;
    private final ApplicationContext context; // Added for proxy calls
    final CustomerService customerService ;


    public UserManagementService(UserRepository userRepository,
                                 PasswordEncoder passWordEncoder,
                                 UserService userService,
                                 UserRoleService userRoleService,

                                 ApplicationContext context, CustomerService customerService) {
        this.userRepository = userRepository;
        this.passWordEncoder = passWordEncoder;
        this.userService = userService;
        this.context = context; // Initialize context
        this.customerService = customerService;
    }

    public  NewAccountResponse  createUser(NewUserRequest newUserRequest) {
        if(newUserRequest.isCompany() && newUserRequest.companyCustomerRegisterRequest() != null) {
            customerService.createCompanyCustomer( newUserRequest.companyCustomerRegisterRequest() , CustomerType.POTENTIAL_CUSTOMER) ;
        }

        if(!newUserRequest.isCompany() && newUserRequest.nonCompanyCustomerRegisterRequest() != null) {
            customerService.createNonCompanyCustomer(newUserRequest.nonCompanyCustomerRegisterRequest() , CustomerType.POTENTIAL_CUSTOMER)  ;
        }

        User newUser = User.builder()
                .email(newUserRequest.email())
                .password(passWordEncoder.encode(newUserRequest.password()))
                .isCompany(newUserRequest.isCompany())
                .enabled(true)
                .createdAt(new Date())
                .build();
        User savedUser = userRepository.saveAndFlush(newUser);
        assignRoleToAUser(savedUser.getUsername(), newUserRequest.rolesId());
        return  new  NewAccountResponse(newUser.getEmail(), newUser.getPassword());
    }


    public void assignRoleToAUser(String userName, List<Long> rolesId) {
        UserRoleAssignementRequest userRoleAssignementRequest = new UserRoleAssignementRequest(
                userName,
                rolesId
        );
        // Use ApplicationContext to ensure transactional proxy is active
        context.getBean(UserRoleService.class).assignUserRolesToAUser(userRoleAssignementRequest);
    }

    public boolean isDatabaseEmpty() {
        return userRepository.count() == 0;
    }
}
