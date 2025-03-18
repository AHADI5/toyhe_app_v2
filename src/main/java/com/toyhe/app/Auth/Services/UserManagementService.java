package com.toyhe.app.Auth.Services;

import com.toyhe.app.Auth.Dtos.Requests.NewExternalUserRequest;
import com.toyhe.app.Auth.Dtos.Requests.NewInUserRequest;
import com.toyhe.app.Auth.Dtos.Requests.UserRoleAssignementRequest;
import com.toyhe.app.Auth.Dtos.Responses.NewAccountResponse;
import com.toyhe.app.Auth.Model.*;
import com.toyhe.app.Auth.Repositories.UserRepository;
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

    public UserManagementService(UserRepository userRepository,
                                 PasswordEncoder passWordEncoder,
                                 UserService userService,
                                 UserRoleService userRoleService,
                                 ApplicationContext context) {
        this.userRepository = userRepository;
        this.passWordEncoder = passWordEncoder;
        this.userService = userService;
        this.context = context; // Initialize context
    }

    public NewAccountResponse createExternalUser(NewExternalUserRequest request) {
        ExternalUser externalUser = ExternalUser.builder()
                .email(request.email())
                .password(passWordEncoder.encode(request.password()))
                .enabled(true)
                .createdAt(new Date())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phoneNumber(request.phoneNumber())
                .gender(request.gender())
                .dateOfBirth(request.dateOfBirth())
                .build();


        ExternalUser savedExternalUser = userRepository.saveAndFlush(externalUser);

        // Assign roles using proxy to ensure transactional behavior
        assignRoleToAUser(savedExternalUser.getUsername(), request.rolesId());

        return new NewAccountResponse(externalUser.getEmail(), externalUser.getPassword());
    }

    public NewAccountResponse createInUser(NewInUserRequest request) {
        InUser inUser = InUser.builder()
                .department(request.service())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .password(passWordEncoder.encode(request.password()))
                .enabled(true)
                .createdAt(new Date())
                .build();



        InUser savedInUser = userRepository.saveAndFlush(inUser);

        // Assign roles using proxy to ensure transactional behavior
        assignRoleToAUser(savedInUser.getUsername(), request.rolesId());

        return new NewAccountResponse(inUser.getEmail(), inUser.getPassword());
    }

    public void assignRoleToAUser(String userName, List<Long> rolesId) {
        UserRoleAssignementRequest userRoleAssignementRequest = new UserRoleAssignementRequest(
                userName,
                rolesId
        );
        // Use ApplicationContext to ensure transactional proxy is active
        context.getBean(UserRoleService.class).assignUserRolesToAUser(userRoleAssignementRequest);
    }
}
