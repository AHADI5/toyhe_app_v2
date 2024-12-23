package com.toyhe.app.Flotte.Dtos.Boat;

import com.toyhe.app.Flotte.Dtos.BoatClasses.BoatClassRegisterRequest;
import com.toyhe.app.Flotte.Models.BoatClass;

import java.util.List;

public record BoatRegisterRequest(
        String name  ,
        String numMatriculation ,
        String motorType  ,
        String fabricationDate   ,
        String boatCategory  ,
        double boatWeight ,
        double supportedWeight ,
        List<BoatClassRegisterRequest> boatClassList
) {
}
