package com.toyhe.app.Auth.Services;

import com.toyhe.app.Auth.Dtos.Requests.AccessRightsRequest;
import com.toyhe.app.Auth.Dtos.Requests.NewExternalUserRequest;
import com.toyhe.app.Auth.Dtos.Requests.NewInUserRequest;
import com.toyhe.app.Auth.Dtos.Responses.AccessRightResponse;
import com.toyhe.app.Auth.Dtos.Responses.NewAccountResponse;
import com.toyhe.app.Auth.Model.*;
import com.toyhe.app.Auth.Repositories.AccessRightsRepository;
import com.toyhe.app.Auth.Repositories.AddressRepository;
import com.toyhe.app.Auth.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserManagementService {
    final UserRepository userRepository ;
    private final PasswordEncoder passWordEncoder;
    final AddressRepository addressRepository ;

    final UserService userService;

    public UserManagementService(UserRepository userRepository,
                                 PasswordEncoder passWordEncoder,
                                 AddressRepository addressRepository,
                                 UserService userService) {
        this.userRepository = userRepository;
        this.passWordEncoder = passWordEncoder;
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    @Transactional
    public NewAccountResponse createExternalUser(NewExternalUserRequest request) {

        // Create a new ExternalUser using the provided request data
        ExternalUser externalUser = ExternalUser
                .builder()
                .role(Role.LAMBDAUSER)
                .email(request.email())
                .password(passWordEncoder.encode(request.password()))
                .enabled(true)
                .createdAt(new Date())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phoneNumber(request.phoneNumber())
                .gender(request.gender())
                .dateOfBirth(request.dateOfBirth())
                .gender(request.gender())
                .build();

        Address savedAddress = addressRepository.save(request.address());
        externalUser.setAddress(savedAddress);
        ExternalUser savedExternalUser = userRepository.save(externalUser);
        //Grant User Access Rights

        userService.grantUserAccessRights(request.authorities(), savedExternalUser);
        userRepository.save(savedExternalUser);

        return new NewAccountResponse(
                externalUser.getEmail() ,
                externalUser.getPassword()
        );

        //Register access rights if defined On User Creation
    }

    public  NewAccountResponse createInUser (NewInUserRequest request) {
        // Default role
        Role role = Role.LAMBDAUSER;
        switch (request.role().toString()) {
            case "Director":
                role = Role.DIRECTOR;
                break;
            case "Admin":
                role = Role.ADMIN;
                break;
            // Add cases for other roles as needed
            default:
                System.out.println("Unknown role: " + request.role());
        }

        InUser inUser = InUser
                .builder()
                .department(request.service())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .password(passWordEncoder.encode(request.password()))
                .enabled(true)
                .createdAt(new Date())
                .role(role)
                .build();
        Address savedAddress = addressRepository.save(request.address());
        inUser.setAddress(savedAddress);
        InUser savedInUser =  userRepository.save(inUser);
        //Grant User Access Rights
        log.info("Access right request {}", request.accessRightsRequests());
        userService.grantUserAccessRights(request.accessRightsRequests(), savedInUser);
        userRepository.save(savedInUser);

        return new NewAccountResponse(
                inUser.getEmail() ,
                inUser.getPassword()
        );

    }

}
