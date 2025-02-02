package com.toyhe.app.Auth.Controller.Authentification;

import com.toyhe.app.Auth.Dtos.Requests.AuthRequest;
import com.toyhe.app.Auth.Dtos.Responses.AuthResponse;
import com.toyhe.app.Auth.Model.UserRole;
import com.toyhe.app.Auth.Services.AuthenticationService;
import com.toyhe.app.Auth.Services.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/auth")
@Slf4j
public record AuthController(
        AuthenticationService authenticationService ,
        UserRoleService userRoleService
) {
    //TODO : AUTHENTICATE ENDPOINT
    @PostMapping("/")
    public AuthResponse authenticate(@RequestBody AuthRequest authRequest) {
        return  authenticationService.authenticate(authRequest);
    }

    @PostMapping("/test")
    public String test(@RequestBody AuthRequest authRequest) {
        log.info("Auth request: {}", authRequest);
        return userRoleService.getUserRoles(authRequest.email()) ;
    }

}
