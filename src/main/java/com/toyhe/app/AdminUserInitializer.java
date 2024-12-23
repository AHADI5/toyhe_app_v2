package com.toyhe.app;

import com.toyhe.app.Auth.Dtos.Requests.AccessOperation;
import com.toyhe.app.Auth.Dtos.Requests.AccessRightsRequest;
import com.toyhe.app.Auth.Model.*;
import com.toyhe.app.Auth.Repositories.AccessRightsRepository;
import com.toyhe.app.Auth.Repositories.ModelRespository;
import com.toyhe.app.Auth.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class AdminUserInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelRespository modelRespository  ;

    @Bean
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
                        .role(Role.ADMIN)  // Set the role to ADMIN
                        .phoneNumber("0790000000")
                        .enabled(true)
                        .build();
                InUser admin = userRepository.save(adminUser);
                System.out.println("Admin user created successfully.");

                //A list that will contain all admin access Right
                List<AccessRightsRequest> accessRightsRequests  = new ArrayList<>();

                //this list will contain all accessRights ( real objects ) for the admin user
                List<AccessRights> adminAccessRights  = new ArrayList<>();
                //get All Modules
                List<Model> models = modelRespository.findAll();

                for (Model model : models) {
                    AccessRightsRequest accessRightsRequest = new AccessRightsRequest(
                            model.getModelID()  ,
                            adminEmail  ,
                            new AccessOperation(1 , 1 ,  1 , 1)
                    );
                    accessRightsRequests.add(accessRightsRequest);

                }

                for (AccessRightsRequest accessRightsRequest : accessRightsRequests) {
                    Model  model = modelRespository.findById(accessRightsRequest.modelID()).get() ;
                    AccessRights accessRights = AccessRights.builder()
                            .model(model)
                            .user(admin)
                            .accessRead(accessRightsRequest.accessOperation().read() == 1)
                            .accessWrite(accessRightsRequest.accessOperation().write() == 1)
                            .accessDelete(accessRightsRequest.accessOperation().delete() == 1)
                            .accessUpdate(accessRightsRequest.accessOperation().update() == 1)
                            .build();
                    adminAccessRights.add(accessRightsRepository.save(accessRights));
                }
                admin.setModulePermissions(adminAccessRights);
                userRepository.save(admin);

            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }
}
