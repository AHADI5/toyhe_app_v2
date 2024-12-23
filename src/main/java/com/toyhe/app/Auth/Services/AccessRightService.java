package com.toyhe.app.Auth.Services;
import com.toyhe.app.Auth.Dtos.Requests.AccessOperation;
import com.toyhe.app.Auth.Dtos.Requests.AccessRightsRequest;
import com.toyhe.app.Auth.Dtos.Responses.AccessRightResponse;
import com.toyhe.app.Auth.Model.AccessRights;
import com.toyhe.app.Auth.Model.Model;
import com.toyhe.app.Auth.Model.User;
import com.toyhe.app.Auth.Repositories.AccessRightsRepository;
import com.toyhe.app.Auth.Repositories.ModelRespository;
import com.toyhe.app.Auth.Repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public record AccessRightService(
        AccessRightsRepository accessRightsRepository,
        UserRepository userRepository,
        ModelRespository modelRespository
) {

    /**
     * Grants access rights to a user for a specific model and operation.
     *
     * @param accessRightsRequest the request containing user, model, and operation details.
     * @return a ResponseEntity containing the granted access right details.
     */
    public ResponseEntity<AccessRightResponse> grantAccessRight(AccessRightsRequest accessRightsRequest) {
        // Fetch the user by email
        Optional<User> user = userRepository.findByEmail(accessRightsRequest.userName());
        if (user.isEmpty()) {
            log.info("User not found");
            return ResponseEntity.badRequest().body(null);
        }

        // Fetch the model by ID
        Optional<Model> model = modelRespository.findById(accessRightsRequest.modelID());
        if (model.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }


        // Create and save the AccessRights entity
        AccessRights accessRights = AccessRights.builder()
                .user(user.get())
                .model(model.get())
                .accessRead(accessRightsRequest.accessOperation().read() == 1)
                .accessWrite(accessRightsRequest.accessOperation().write() == 1)
                .accessDelete(accessRightsRequest.accessOperation().delete() == 1)
                .accessUpdate(accessRightsRequest.accessOperation().update() == 1)
                .build();

        AccessRights savedAccessRights = accessRightsRepository.save(accessRights);

        // Prepare the response DTO
        AccessRightResponse accessRightResponse = new AccessRightResponse(
                savedAccessRights.getId(),
                savedAccessRights.getModel().getModelID(),
                savedAccessRights.getModel().getModelName(),
                savedAccessRights.getUser().getUsername() ,
                new AccessOperation(
                        savedAccessRights.isAccessRead() ? 1 : 0 ,
                        savedAccessRights.isAccessWrite() ?  1 :0  ,
                        savedAccessRights.isAccessUpdate() ?  1 : 0  ,
                        savedAccessRights.isAccessDelete() ?  1 :0

                )
        );

        return ResponseEntity.ok(accessRightResponse);
    }

    public ResponseEntity<AccessRightResponse> updateAccessRight(AccessRightsRequest accessRightsRequest , long accessRightID) {
       AccessRights accessRights = accessRightsRepository.findById(accessRightID).orElse(null);
       if (accessRights == null) {
           return ResponseEntity.badRequest().body(null);
       } else  {
           accessRights.setAccessRead(accessRightsRequest.accessOperation().read() == 1);
           accessRights.setAccessWrite(accessRightsRequest.accessOperation().write() == 1);
           accessRights.setAccessDelete(accessRightsRequest.accessOperation().delete() == 1);
           accessRightsRepository.save(accessRights);
       }

       return ResponseEntity.ok(new AccessRightResponse(
               accessRights.getId()  ,
               accessRights.getModel().getModelID() ,
               accessRights.getModel().getModelName() ,
               accessRights.getUser().getUsername() ,
               new AccessOperation(
                       accessRights.isAccessRead() ? 1 : 0 ,
                       accessRights.isAccessWrite() ? 1 : 0  ,
                       accessRights.isAccessUpdate() ? 1 : 0 ,
                       accessRights.isAccessDelete() ? 1 : 0
               )
       )) ;
    }

    public List<AccessRightResponse> getAllAccessRights() {
       List<AccessRights> accessRightsList = accessRightsRepository.findAll();
        return convertToAccessRightResponses(accessRightsList);
    }

    public AccessRightResponse getAccessRight(long accessRightID) {
        Optional<AccessRights> accessRights = accessRightsRepository.findById(accessRightID);
        return accessRights.map(this::convertToAccessRightResponse).orElse(null);
    }

    /**
     * Converts a list of AccessRights to AccessRightResponse objects.
     *
     * @param accessRightsList the list of AccessRights to convert.
     * @return a list of AccessRightResponse objects.
     */
    public List<AccessRightResponse> convertToAccessRightResponses(List<AccessRights> accessRightsList) {
        List<AccessRightResponse> accessRightResponses = new ArrayList<>();

        for (AccessRights accessRights : accessRightsList) {
            accessRightResponses.add(new AccessRightResponse(
                    accessRights.getId(),
                    accessRights.getModel().getModelID(),
                    accessRights.getModel().getModelName(),
                    accessRights.getUser().getUsername(),
                    new AccessOperation(
                            accessRights.isAccessRead() ? 1 : 0,
                            accessRights.isAccessWrite() ? 1 : 0,
                            accessRights.isAccessUpdate() ? 1 : 0,
                            accessRights.isAccessDelete() ? 1 : 0
                    )
            ));
        }

        return accessRightResponses;
    }

    /**
     * Converts a single AccessRights object to an AccessRightResponse.
     *
     * @param accessRights the AccessRights object to convert.
     * @return the AccessRightResponse object.
     */
    public AccessRightResponse convertToAccessRightResponse(AccessRights accessRights) {
        return new AccessRightResponse(
                accessRights.getId(),
                accessRights.getModel().getModelID(),
                accessRights.getModel().getModelName(),
                accessRights.getUser().getUsername(),
                new AccessOperation(
                        accessRights.isAccessRead() ? 1 : 0,
                        accessRights.isAccessWrite() ? 1 : 0,
                        accessRights.isAccessUpdate() ? 1 : 0,
                        accessRights.isAccessDelete() ? 1 : 0
                )
        );
    }

    public List<AccessRightResponse> getAccessRightsByUsername(String userName) {
        User user  = userRepository.findByEmail(userName).orElse(null);
        List<AccessRights> accessRights = accessRightsRepository.findAccessRightsByUser(user);
        return convertToAccessRightResponses(accessRights);

    }
}
