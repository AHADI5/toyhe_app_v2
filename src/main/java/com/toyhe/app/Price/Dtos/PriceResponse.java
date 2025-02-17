package com.toyhe.app.Price.Dtos;

import com.toyhe.app.Price.Model.Price;

import java.time.LocalDateTime;

public record PriceResponse(
        long priceID  ,
        String priceName ,
        double amount  ,
        LocalDateTime startDate ,
        LocalDateTime enDate ,
        Boolean isDefault
) {

    public static  PriceResponse toPriceResponse(Price price) {
        return new PriceResponse(
                price.getPriceID()  ,
                price.getPriceName()  ,
                price.getAmount()  ,
                price.getStartDate()  ,
                price.getEndDate()  ,
                price.getIsDefault()
        );

    }
}
