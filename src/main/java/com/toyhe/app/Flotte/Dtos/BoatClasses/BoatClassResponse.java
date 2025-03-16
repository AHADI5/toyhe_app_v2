package com.toyhe.app.Flotte.Dtos.BoatClasses;

import com.toyhe.app.Flotte.Models.BoatClass;

public record BoatClassResponse(
        int boatClassId,
        String name  ,
        long boatID ,
        String boatName  ,
        double priceListID,
        double placeAvailable

) {
    public static BoatClassResponse fromBoatClassToDTO(BoatClass boatClass) {
        return new BoatClassResponse(
                Math.toIntExact(boatClass.getBoatClassID()),
                boatClass.getName()  ,
                boatClass.getBoat().getBoatID()  ,
                boatClass.getBoat().getName()  ,
                0 ,
                boatClass.getPlacesNumber()
        ) ;
    }
}
