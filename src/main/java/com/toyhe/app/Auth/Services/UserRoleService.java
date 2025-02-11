package com.toyhe.app.Auth.Services;

import com.toyhe.app.Auth.Dtos.Requests.AccessRightsRequest;
import com.toyhe.app.Auth.Dtos.Requests.UserRoleAssignementRequest;
import com.toyhe.app.Auth.Dtos.Requests.UserRoleRequest;
import com.toyhe.app.Auth.Dtos.Responses.AccessRightResponse;
import com.toyhe.app.Auth.Dtos.Responses.UserResponse;
import com.toyhe.app.Auth.Dtos.Responses.UserRoleAssignmentResponse;
import com.toyhe.app.Auth.Dtos.Responses.UserRoleResponse;
import com.toyhe.app.Auth.Model.AccessRights;
import com.toyhe.app.Auth.Model.User;
import com.toyhe.app.Auth.Model.UserRole;
import com.toyhe.app.Auth.Repositories.AccessRightsRepository;
import com.toyhe.app.Auth.Repositories.UserRepository;
import com.toyhe.app.Auth.Repositories.UserRoleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserRoleService{
    public final UserRoleRepository userRoleRepository;
    public final UserService userService ;
    public final AccessRightService accessRightService;
    public final AccessRightsRepository accessRightsRepository ;
    public final  UserRepository userRepository ;

    public UserRoleService(UserRoleRepository userRoleRepository, UserService userService, AccessRightService accessRightService, AccessRightsRepository accessRightsRepository, UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
        this.accessRightService = accessRightService;
        this.accessRightsRepository = accessRightsRepository;
        this.userRepository = userRepository;
    }

    public UserRoleResponse createUserRole(UserRoleRequest userRoleRequest) {
        if (userRoleRequest == null || userRoleRequest.roleName() == null) {
            throw new IllegalArgumentException("UserRoleRequest or role name cannot be null");
        }

        // Check if the role already exists
        if (userRoleRepository.existsByRoleName(userRoleRequest.roleName())) {
            throw new IllegalArgumentException("Role with name '" + userRoleRequest.roleName() + "' already exists");
        }

        // Build the user role entity
        UserRole userRole = UserRole.builder()
                .roleName(userRoleRequest.roleName())
                .roleDescription(userRoleRequest.description() != null ? userRoleRequest.description() : "No description provided")
                .build();

        try {
            // Save the user role
            UserRole savedUserRole = userRoleRepository.save(userRole);

            return new UserRoleResponse(
                    savedUserRole.getRoleName(),
                    savedUserRole.getRoleDescription(),
                    null // Adjust if needed
            );
        } catch (Exception e) {
            throw new RuntimeException("Error saving user role: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<UserRoleAssignmentResponse> assignUserRolesToAUser(UserRoleAssignementRequest request) {
        // Get the user
        User user = userService.findUserByUsername(request.userName()).getBody();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Fetch roles from database
        List<UserRole> newRoles = new ArrayList<>(userRoleRepository.findAllById(request.userRoleIds()));
        log.info("Fetched new roles: {}", newRoles.stream().map(UserRole::getRoleName).toList());
        log.info("Existing roles: {}",
                user.getUserRoles() != null
                        ? user.getUserRoles().stream().map(UserRole::getRoleDescription).toList()
                        : "No existing roles");

// Check if the existing roles are null
        Set<UserRole> mergedRoles;
        if (user.getUserRoles() == null) {
            // If null, initialize with new roles
            mergedRoles = new HashSet<>(newRoles);
            log.info("No existing roles found. Adding new roles: {}", newRoles.stream().map(UserRole::getRoleName).toList());
        } else {
            // Merge new roles with existing roles
            mergedRoles = new HashSet<>(user.getUserRoles());
            mergedRoles.addAll(newRoles);
            log.info("Merged roles: {}", mergedRoles.stream().map(UserRole::getRoleName).toList());
        }

// Update the user's roles
        user.setUserRoles(new ArrayList<>(mergedRoles));


        log.info("Merged roles: {}", mergedRoles.stream().toList());

        // Update the user's roles with the merged set (or if you prefer a List, convert it)
        user.setUserRoles(new ArrayList<>(mergedRoles));

        // Save user with updated roles
        User updatedUser = userService.saveUser(user);

        // Convert to DTO response
        List<UserRoleResponse> roleResponses = updatedUser.getUserRoles().stream()
                .map(role -> new UserRoleResponse(role.getRoleName(), role.getRoleDescription(), null))
                .collect(Collectors.toList());

        log.info("User Role assignment response: {}", roleResponses);

        return ResponseEntity.ok(new UserRoleAssignmentResponse(new UserResponse(user.getUsername()), roleResponses));
    }



    public List<AccessRightResponse> grantUserRoleAccessRights(List<AccessRightsRequest> requests, long userRoleID) {
        // Find the user role
        UserRole userRole = userRoleRepository.findById(userRoleID).orElse(null);
        if (userRole == null) return Collections.emptyList();

        // Grant access rights and store responses
        List<AccessRightResponse> responses = requests.stream()
                .map(req -> accessRightService.grantAccessRight(req).getBody())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        log.info("AccessRight Responses: {}", responses);

        // Convert to AccessRights entities
        List<AccessRights> accessRights = responses.stream()
                .map(resp -> accessRightsRepository.findById(resp.accessRightID()).orElse(null))
                .filter(Objects::nonNull)
                .toList();

        // Instead of replacing the collection reference, update the persistent collection:
        List<AccessRights> currentPermissions = userRole.getModulePermissions();
        currentPermissions.clear();
        currentPermissions.addAll(accessRights);

        // Save the changes
        userRoleRepository.save(userRole);

        return responses;
    }


    public String getUserRoles(String username) {

        Optional<User> user = userRepository.findByEmail(username);
        return user.get().getUserRoles().stream().map(UserRole::getRoleName).collect(Collectors.joining(","));
    }
}
