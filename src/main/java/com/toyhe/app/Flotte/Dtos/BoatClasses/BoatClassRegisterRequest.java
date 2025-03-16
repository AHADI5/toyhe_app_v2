package com.toyhe.app.Flotte.Dtos.BoatClasses;

public record BoatClassRegisterRequest(
        String name,
        int placesNumber ,
        long boatID  ,
        long priceListID

) {

}
