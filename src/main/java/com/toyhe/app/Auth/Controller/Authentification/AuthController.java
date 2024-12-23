package com.toyhe.app.Auth.Controller.Authentification;

import com.toyhe.app.Auth.Dtos.Requests.AuthRequest;
import com.toyhe.app.Auth.Dtos.Responses.AuthResponse;
import com.toyhe.app.Auth.Services.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public record AuthController(
        AuthenticationService authenticationService
) {
    //TODO : AUTHENTICATE ENDPOINT
    @PostMapping("/")
    public AuthResponse authenticate(@RequestBody AuthRequest authRequest) {
        return  authenticationService.authenticate(authRequest);
    }



}
