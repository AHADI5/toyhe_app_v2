package com.toyhe.app.Flotte.Dtos.BoatClasses;

public record BoatClassRegisterRequest(
        String name,
        double placesNumber ,
        long boatID  ,
        long priceListID

) {

}
