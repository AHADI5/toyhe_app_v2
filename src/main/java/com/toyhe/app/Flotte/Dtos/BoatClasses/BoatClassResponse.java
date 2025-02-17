package com.toyhe.app.Flotte.Dtos.BoatClasses;

import com.toyhe.app.Flotte.Models.BoatClass;

public record BoatClassResponse(
        String name  ,
        long boatID ,
        String boatName  ,
        double priceListID,
        double placeAvailable
) {
    public static BoatClassResponse fromBoatClassToDTO(BoatClass boatClass) {
        return new BoatClassResponse(
                boatClass.getName()  ,
                boatClass.getBoat().getBoatID()  ,
                boatClass.getBoat().getName()  ,
                boatClass.getPrice().getAmount() ,
                boatClass.getPlacesNumber()
        ) ;
    }
}
