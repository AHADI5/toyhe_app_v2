package com.toyhe.app.Trips.Dtos;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record TripRegisterRequest(
        LocalDateTime departureDate ,
        LocalDateTime arrivalDate  ,
        String days,
        long boatID,
        List<Long> classIDs,
        String tripType,
        long routeID ,
        int expectedComeBackInHours ,
        int durationInWeeks ,
        int tag
) {

}
