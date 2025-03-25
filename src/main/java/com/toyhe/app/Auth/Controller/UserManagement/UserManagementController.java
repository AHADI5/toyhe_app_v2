package com.toyhe.app.Auth.Controller.UserManagement;

import com.toyhe.app.Auth.Dtos.Requests.NewUserRequest;
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
    static  String modelName = "USER" ;
    @PostMapping("/")
    public NewAccountResponse registerNewInAccount(@RequestBody
                                                       NewUserRequest newUserRequest ,
                                                   HttpServletRequest httpServletRequest
                                                   ) {
        checkAccess.checkAccess(httpServletRequest, modelName, "WRITE_UPDATE");
        return userManagementService.createUser(newUserRequest);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test successful , with deployment");
    }
}
