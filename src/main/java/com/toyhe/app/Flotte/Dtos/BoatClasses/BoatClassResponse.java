package com.toyhe.app.Flotte.Dtos.BoatClasses;

import com.toyhe.app.Flotte.Models.BoatClass;
import com.toyhe.app.Price.Dtos.PriceResponse;
import com.toyhe.app.Products.Dtos.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

public record BoatClassResponse(
        int boatClassId,
        String name  ,
        long boatID ,
        String boatName  ,
        PriceResponse priceResponse   ,
        int placeAvailable ,
        List<ProductResponse> productResponseList



) {
    public static BoatClassResponse fromBoatClassToDTO(BoatClass boatClass) {
        return new BoatClassResponse(
                Math.toIntExact(boatClass.getBoatClassID()),
                boatClass.getName()  ,
                boatClass.getBoat().getBoatID()  ,
                boatClass.getBoat().getName()  ,
                boatClass.getPriceModel() == null  ?  null : PriceResponse.toPriceResponse(boatClass.getPriceModel()),
                boatClass.getPlacesNumber() ,
                boatClass.getProducts()  != null
                        ? boatClass.getProducts().stream()
                        .map(ProductResponse::fromModel)
                        .collect(Collectors.toList())
                        : List.of()
        ) ;
    }
}
