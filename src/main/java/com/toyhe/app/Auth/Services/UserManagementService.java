package com.toyhe.app.Auth.Services;

import com.toyhe.app.Auth.Dtos.Requests.AccessRightsRequest;
import com.toyhe.app.Auth.Dtos.Requests.NewExternalUserRequest;
import com.toyhe.app.Auth.Dtos.Requests.NewInUserRequest;
import com.toyhe.app.Auth.Dtos.Requests.UserRoleAssignementRequest;
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
    private final UserRoleService userRoleService;

    public UserManagementService(UserRepository userRepository,
                                 PasswordEncoder passWordEncoder,
                                 AddressRepository addressRepository,
                                 UserService userService, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.passWordEncoder = passWordEncoder;
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    public NewAccountResponse createExternalUser(NewExternalUserRequest request) {

        // Create a new ExternalUser using the provided request data
        ExternalUser externalUser = ExternalUser
                .builder()
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

        //Assigning Roles to the user
        assignRoleToAUser(externalUser.getUsername() , request.rolesId());
        userRepository.save(savedExternalUser);

        return new NewAccountResponse(
                externalUser.getEmail() ,
                externalUser.getPassword()
        );

        //Register access rights if defined On User Creation
    }

    public  NewAccountResponse createInUser (NewInUserRequest request) {


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
                .build();
        Address savedAddress = addressRepository.save(request.address());
        inUser.setAddress(savedAddress);
        InUser savedInUser =  userRepository.save(inUser);
        //Assign User to Role
        assignRoleToAUser(savedInUser.getUsername() , request.rolesId());

        userRepository.save(savedInUser);

        return new NewAccountResponse(
                inUser.getEmail() ,
                inUser.getPassword()
        );

    }

    public  void assignRoleToAUser(String userName, List<Long> rolesId) {
        //Assign user to the existing Role
        UserRoleAssignementRequest  userRoleAssignementRequest = new UserRoleAssignementRequest(
                userName,
                rolesId
        ) ;
        userRoleService.assignUserRolesToAUser(userRoleAssignementRequest) ;
    }

}
