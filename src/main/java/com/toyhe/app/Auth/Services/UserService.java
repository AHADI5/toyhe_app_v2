package com.toyhe.app.Auth.Services;

import com.toyhe.app.Auth.Dtos.Requests.AccessRightsRequest;
import com.toyhe.app.Auth.Dtos.Responses.AccessRightResponse;
import com.toyhe.app.Auth.Model.AccessRights;
import com.toyhe.app.Auth.Model.User;
import com.toyhe.app.Auth.Repositories.AccessRightsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public record UserService(
        AccessRightService accessRightService ,
        AccessRightsRepository accessRightsRepository
) {
    public void grantUserAccessRights(List<AccessRightsRequest> accessRightsRequest , User user ) {
        //List of access right request
        List<AccessRightResponse> accessRightsResponseList = new ArrayList<>();

        for (AccessRightsRequest accessRightsRequestItem : accessRightsRequest) {
            AccessRightResponse accessRightResponse = accessRightService.grantAccessRight(accessRightsRequestItem).getBody();
            accessRightsResponseList.add(accessRightResponse);
            log.info("AccessRight Response item {}", accessRightResponse);
        }

        log.info("AccessRight Response {}", accessRightsResponseList);

        //Converting accessRight Response to AccessRight
        List<AccessRights> accessRights = new ArrayList<>();
        for (AccessRightResponse accessRightResponse : accessRightsResponseList) {
            accessRights.add(accessRightsRepository.findById(accessRightResponse.accessRightID()).orElse(null));
        }

        user.setModulePermissions(accessRights);

    }
}
