package com.toyhe.app.Auth.Controller.UserManagement;

import com.toyhe.app.Auth.Dtos.Requests.AccessRightsRequest;
import com.toyhe.app.Auth.Dtos.Responses.AccessRightResponse;
import com.toyhe.app.Auth.Services.AccessRightService;
import com.toyhe.app.Auth.config.CheckAccess;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/access")
public record AccessRightControllers(
        AccessRightService accessRightService,
        CheckAccess checkAccess

) {
    static String modelName = "ACCESSRIGHTS";
    @PostMapping("/")
    public ResponseEntity<AccessRightResponse> grantAccessRight(
            @RequestBody AccessRightsRequest accessRightsRequest, HttpServletRequest httpServletRequest) {
        checkAccess.checkAccess(httpServletRequest, modelName, "WRITE_UPDATE");
        return  accessRightService.grantAccessRight(accessRightsRequest);
    }

    @GetMapping("/allAccess")
    public List<AccessRightResponse> getAllAccessRights(HttpServletRequest httpServletRequest) {
        checkAccess.checkAccess(httpServletRequest, modelName, "READ");
        return accessRightService.getAllAccessRights() ;

    }

    @GetMapping("accessRights/{userRoleName}")
    public List<AccessRightResponse> getAccessRightsByUSerRoleName(@PathVariable String userRoleName, HttpServletRequest httpServletRequest) {
         //checkAccess.checkAccess(httpServletRequest, modelName, "READ");
        return accessRightService.getAccessRightsByUserRole(userRoleName) ;
    }

    @GetMapping("/{accessID}")
    public AccessRightResponse getAccessRight(@PathVariable String accessID, HttpServletRequest httpServletRequest) {
        checkAccess.checkAccess(httpServletRequest, modelName, "READ");
        return accessRightService.getAccessRight(Long.parseLong(accessID)) ;
    }

    @PutMapping("/updateAccess/{accessID}")
    public ResponseEntity<AccessRightResponse> updateAccessRight(
            @RequestBody AccessRightsRequest accessRightsRequest,
            @PathVariable long accessID, HttpServletRequest httpServletRequest) {
        checkAccess.checkAccess(httpServletRequest, modelName, "WRITE_UPDATE");
        return  accessRightService.updateAccessRight(accessRightsRequest, accessID);
    }
}
