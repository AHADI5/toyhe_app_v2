package com.toyhe.app.Price.Dtos;

import java.time.LocalDateTime;

public record PriceRequest(
        double amount   ,
        LocalDateTime  startDate ,
        LocalDateTime endDate ,
        String deviseName  ,
        Boolean isDefault
) {
}
