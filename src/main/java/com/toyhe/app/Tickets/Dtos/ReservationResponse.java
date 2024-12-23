package com.toyhe.app.Tickets.Dtos;

import com.toyhe.app.Flotte.Models.BoatClass;

import java.time.LocalDateTime;

public record ReservationResponse(
        String customerName ,
        LocalDateTime reserationDate  ,
        LocalDateTime tripDate  ,
        String boatName  ,
        BoatClass className  ,
        double price
) {

}
