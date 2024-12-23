package com.toyhe.app.Flotte.Dtos.BoatClasses;

public record BoatClassRegisterRequest(
        Long boatClassID,
        String name,
        double placesNumber  ,
        long boatID

) {

}
