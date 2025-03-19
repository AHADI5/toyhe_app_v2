package com.toyhe.app.Flotte.Dtos.BoatClasses;

import com.toyhe.app.Flotte.Models.BoatClass;
import com.toyhe.app.Products.Dtos.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

public record BoatClassResponse(
        int boatClassId,
        String name  ,
        long boatID ,
        String boatName  ,
        double priceListID,
        int placeAvailable ,
        List<ProductResponse> productResponseList

) {
    public static BoatClassResponse fromBoatClassToDTO(BoatClass boatClass) {
        return new BoatClassResponse(
                Math.toIntExact(boatClass.getBoatClassID()),
                boatClass.getName()  ,
                boatClass.getBoat().getBoatID()  ,
                boatClass.getBoat().getName()  ,
                boatClass.getPriceModel() == null  ? 0  : boatClass.getPriceModel().getPriceID(),
                boatClass.getPlacesNumber() ,
                boatClass.getProducts()  != null
                        ? boatClass.getProducts().stream()
                        .map(ProductResponse::fromModel)
                        .collect(Collectors.toList())
                        : List.of()
        ) ;
    }
}
