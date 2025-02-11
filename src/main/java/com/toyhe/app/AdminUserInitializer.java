package com.toyhe.app;

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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AdminUserInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelRespository modelRespository;
    private final UserRoleService userRoleService;
    private final UserRoleRepository userRoleRepository;
    private final ApplicationContext context; // Inject ApplicationContext

    @Bean
    @Transactional
    public ApplicationRunner initializeAdmin(AccessRightsRepository accessRightsRepository) {
        return args -> {
            String adminEmail = "admin@company.com";
            Optional<User> user = userRepository.findByEmail(adminEmail);

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
                InUser admin = userRepository.saveAndFlush(adminUser); // Force flush
                System.out.println("Admin user created successfully.");

                // Create Admin Role
                UserRole userRole = UserRole.builder()
                        .roleName("ADMIN")
                        .roleDescription("This is an admin role.")
                        .build();
                UserRole adminUserRole = userRoleRepository.save(userRole);
                System.out.println("Admin user role created successfully.");

                // Assign Role to User via Spring Proxy
                UserRoleAssignementRequest userRoleAssignementRequest = new UserRoleAssignementRequest(
                        admin.getEmail(),
                        Collections.singletonList(adminUserRole.getRoleId())
                );
                context.getBean(UserRoleService.class).assignUserRolesToAUser(userRoleAssignementRequest);

                // Assign Access Rights
                List<Model> models = modelRespository.findAll();
                List<AccessRights> adminAccessRights = new ArrayList<>();

                for (Model model : models) {
                    AccessRights accessRights = AccessRights.builder()
                            .model(model)
                            .userRole(userRole)
                            .accessRead(true)
                            .accessWrite(true)
                            .accessDelete(true)
                            .accessUpdate(true)
                            .build();
                    adminAccessRights.add(accessRightsRepository.save(accessRights));
                }

                userRole.setModulePermissions(adminAccessRights);
                userRoleRepository.save(userRole);

                System.out.println("Admin access rights assigned successfully.");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }
}
