package com.toyhe.app.Auth.Services;

import com.toyhe.app.Auth.Dtos.Requests.AccessRightsRequest;
import com.toyhe.app.Auth.Dtos.Responses.AccessRightResponse;
import com.toyhe.app.Auth.Model.AccessRights;
import com.toyhe.app.Auth.Model.User;
import com.toyhe.app.Auth.Model.UserRole;
import com.toyhe.app.Auth.Repositories.AccessRightsRepository;
import com.toyhe.app.Auth.Repositories.UserRepository;
import com.toyhe.app.Auth.Repositories.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public record UserService(
        AccessRightService accessRightService ,
        AccessRightsRepository accessRightsRepository ,

        UserRepository userRepository  ,
        UserRoleRepository userRoleRepository
) {


    public ResponseEntity<User> findUserByUsername(String username) {
        Optional<User> user  =  userRepository.findByEmail(username) ;

        if (user.isEmpty()) {
            log.info("User not found");
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(user.get());
    }

    public User saveUser(User user) {
        return  userRepository.save(user);
    }


}
