package com.toyhe.app.Flotte.Dtos.BoatClasses;

import com.toyhe.app.Flotte.Models.BoatClass;

public record BoatClassResponse(
        String name  ,
        long boatID ,
        String boatName  ,
        double priceListID
) {
    public static BoatClassResponse fromBoatClassToDTO(BoatClass boatClass) {
        return new BoatClassResponse(
                boatClass.getName()  ,
                boatClass.getBoat().getBoatID()  ,
                boatClass.getBoat().getName()  ,
                boatClass.getBoatClassPrice()
        ) ;
    }
}
