package com.toyhe.app.Flotte.Dtos.BoatClasses;

import java.util.List;

public record BoatClassRegisterRequest(
        String name,
        int placesNumber ,
        long boatID  ,
        long priceListID ,
        List<Integer> productID

) {

}
