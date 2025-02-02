package com.toyhe.app.Auth.Dtos.Responses;

import com.toyhe.app.Auth.Dtos.Requests.AccessOperation;
import com.toyhe.app.Auth.Model.Operations;

public record AccessRightResponse(
        long accessRightID  ,
        long modelID  ,
        String modelName,
        String roleName  ,
        AccessOperation accessOperation

)  {
}
