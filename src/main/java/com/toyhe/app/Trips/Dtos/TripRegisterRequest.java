package com.toyhe.app.Trips.Dtos;

import java.time.LocalDateTime;
import java.util.Date;

public record TripRegisterRequest(
        LocalDateTime departureDate ,
        LocalDateTime arrvialDate  ,
        String days  ,
        long boatID  ,
        long boatClass  ,
        String tripType  ,
        String route
) {

}
