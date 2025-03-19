package com.toyhe.app.Price.Dtos;

import com.toyhe.app.Price.Model.PriceModel;

import java.time.LocalDateTime;

public record PriceResponse(
        long priceID  ,
        String priceName ,
        double amount  ,
        LocalDateTime startDate ,
        LocalDateTime enDate ,
        Boolean isDefault ,
        String deviseName
) {

    public static  PriceResponse toPriceResponse(PriceModel priceModel) {
        return new PriceResponse(
                priceModel.getPriceID()  ,
                priceModel.getPriceName()  ,
                priceModel.getAmount()  ,
                priceModel.getStartDate()  ,
                priceModel.getEndDate()  ,
                priceModel.getIsDefault() ,
                priceModel.getDeviseName()
        );

    }
}
