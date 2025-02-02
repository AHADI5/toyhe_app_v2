package com.toyhe.app;

import com.toyhe.app.Auth.Dtos.Requests.AccessOperation;
import com.toyhe.app.Auth.Dtos.Requests.AccessRightsRequest;
import com.toyhe.app.Auth.Dtos.Requests.UserRoleAssignementRequest;
import com.toyhe.app.Auth.Model.*;
import com.toyhe.app.Auth.Repositories.AccessRightsRepository;
import com.toyhe.app.Auth.Repositories.ModelRespository;
import com.toyhe.app.Auth.Repositories.UserRepository;
import com.toyhe.app.Auth.Repositories.UserRoleRepository;
import com.toyhe.app.Auth.Services.UserRoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@Slf4j

public class AdminUserInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelRespository modelRespository  ;
    private final UserRoleService userRoleService ;
    private final UserRoleRepository userRoleRepository ;

    @Bean
    @Transactional
    public ApplicationRunner initializeAdmin(AccessRightsRepository accessRightsRepository) {
        return args -> {
            String adminEmail = "admin@company.com";
            Optional<User> user  = userRepository.findByEmail(adminEmail) ;



            // Check if admin user already exists
            if (user.isEmpty()) {
                // Create the admin user


                InUser adminUser = InUser.builder()
                        .firstName("Admin")
                        .lastName("User")
                        .email(adminEmail)
                        .password(passwordEncoder.encode("AdminPass2024!"))
                        .phoneNumber("0790000000")
                        .enabled(true)
                        .build();
                InUser admin = userRepository.save(adminUser);
                System.out.println("Admin user created successfully.");

                //Create a Admin Role
                UserRole userRole  = UserRole.builder()
                        .roleName("ADMIN")
                        .roleDescription("This is an admin role.")
                        .build();
                UserRole adminUserRole  = userRoleRepository.save(userRole);
                System.out.println("Admin user role created successfully.");
                //Adding the created role to the created user roles
                //1. Building the request
                UserRoleAssignementRequest userRoleAssignementRequest = new UserRoleAssignementRequest(
                        admin.getEmail()  ,
                        Collections.singletonList(adminUserRole.getRoleId())
                ) ;

                log.info("User assignement request made successfully : {}" , userRoleAssignementRequest );
                userRoleService.assignUserRolesToAUser(userRoleAssignementRequest);

                //A list that will contain all admin access Right
                List<AccessRightsRequest> accessRightsRequests  = new ArrayList<>();

                //this list will contain all accessRights ( real objects ) for the admin role
                List<AccessRights> adminAccessRights  = new ArrayList<>();
                //get All Modules
                List<Model> models = modelRespository.findAll();

                for (Model model : models) {
                    AccessRightsRequest accessRightsRequest = new AccessRightsRequest(
                            model.getModelID()  ,
                            userRole.getRoleId(),
                            new AccessOperation(1 , 1 ,  1 , 1)
                    );
                    accessRightsRequests.add(accessRightsRequest);

                }

                for (AccessRightsRequest accessRightsRequest : accessRightsRequests) {
                    Model  model = modelRespository.findById(accessRightsRequest.modelID()).get() ;
                    AccessRights accessRights = AccessRights.builder()
                            .model(model)
                            .userRole(userRole)
                            .accessRead(accessRightsRequest.accessOperation().read() == 1)
                            .accessWrite(accessRightsRequest.accessOperation().write() == 1)
                            .accessDelete(accessRightsRequest.accessOperation().delete() == 1)
                            .accessUpdate(accessRightsRequest.accessOperation().update() == 1)
                            .build();
                    adminAccessRights.add(accessRightsRepository.save(accessRights));
                }
                userRole.setModulePermissions(adminAccessRights);
                userRepository.save(admin);
                userRoleRepository.save(userRole);

            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }
}
