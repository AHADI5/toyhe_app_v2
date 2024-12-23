package com.toyhe.app.Auth.Controller.UserManagement;

import com.toyhe.app.Auth.Dtos.Requests.NewExternalUserRequest;
import com.toyhe.app.Auth.Dtos.Requests.NewInUserRequest;
import com.toyhe.app.Auth.Dtos.Responses.NewAccountResponse;
import com.toyhe.app.Auth.Services.UserManagementService;
import com.toyhe.app.Auth.config.CheckAccess;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public record UserManagementController(
        UserManagementService userManagementService  ,
        CheckAccess   checkAccess
) {
    static  String modelName = "Users" ;
    @PostMapping("/in_user")
    public NewAccountResponse registerNewInAccount(@RequestBody
                                                   NewInUserRequest newInUserRequest ,
                                                   HttpServletRequest httpServletRequest
                                                   ) {
        checkAccess.checkAccess(httpServletRequest, modelName, "WRITE_UPDATE");
        return userManagementService.createInUser(newInUserRequest);

    }

    @PostMapping("/external_user")
    public NewAccountResponse registerExternalUser (@RequestBody
                                                    NewExternalUserRequest newExternalUserRequest  ,
                                                    HttpServletRequest httpServletRequest) {
        checkAccess.checkAccess(httpServletRequest, modelName, "WRITE_UPDATE");
        return userManagementService.createExternalUser(newExternalUserRequest);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test successful");
    }
}
