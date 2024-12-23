package com.toyhe.app.Auth.Services;

import com.toyhe.app.Auth.Dtos.Requests.AuthRequest;
import com.toyhe.app.Auth.Dtos.Responses.AuthResponse;
import com.toyhe.app.Auth.Model.User;
import com.toyhe.app.Auth.Repositories.UserRepository;
import com.toyhe.app.Auth.config.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record AuthenticationService(
        PasswordEncoder passwordEncoder,
        JwtService jwtService,
        AuthenticationManager authenticationManager,
        UserRepository userRepository
) {

    public AuthResponse authenticate(AuthRequest authRequest) {
        /*
        * this method is about authenticating the user
        * it takes in parameter , an object "authRequest" which represents
        * user credentials.
        * if the users exists and password correct  , ok
        * if the user doesn't exist , forbidden , 503
        * */

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(),
                        authRequest.password()
                )
        );

        User user = userRepository.findByEmail(authRequest.email())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var jwToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        return  new AuthResponse(
                jwToken
        );
    }





}
