package com.toyhe.app.Auth.Controller.UserManagement;

import com.toyhe.app.Auth.Dtos.Requests.AccessRightsRequest;
import com.toyhe.app.Auth.Dtos.Requests.UserRoleAssignementRequest;
import com.toyhe.app.Auth.Dtos.Requests.UserRoleRequest;
import com.toyhe.app.Auth.Dtos.Responses.AccessRightResponse;
import com.toyhe.app.Auth.Dtos.Responses.UserRoleAssignmentResponse;
import com.toyhe.app.Auth.Dtos.Responses.UserRoleResponse;
import com.toyhe.app.Auth.Repositories.UserRoleRepository;
import com.toyhe.app.Auth.Services.UserRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/userRole")
public record UserRoleControllers(
        UserRoleService userRoleService
) {
    @PostMapping("/")
    public UserRoleResponse createUserRole(@RequestBody  UserRoleRequest userRoleRequest) {
        return userRoleService.createUserRole(userRoleRequest)  ;
    }

    @PostMapping("/{roleID}/grantAccessRights")
    public List<AccessRightResponse> grantUserRoleAccessRights(
            @PathVariable long roleID  , @RequestBody List<AccessRightsRequest> accessRightsRequest
    ) {
        return  userRoleService.grantUserRoleAccessRights(accessRightsRequest , roleID)  ;
    }

    @PostMapping("/assignUserToRole")
    public ResponseEntity<UserRoleAssignmentResponse> assignUserRoleToRole(@RequestBody UserRoleAssignementRequest userRoleAssignementRequest) {
        return  userRoleService.assignUserRolesToAUser(userRoleAssignementRequest)  ;
    }

    @GetMapping("/userRoles/{userName}")
    public String getUserRoleByUserName(@PathVariable String userName) {
        return userRoleService.getUserRoles(userName)  ;
    }

}
