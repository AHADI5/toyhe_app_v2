package com.toyhe.app.Trips.Dtos;


import com.toyhe.app.Flotte.Dtos.BoatClasses.BoatClassResponse;
import com.toyhe.app.Trips.Models.Days;
import com.toyhe.app.Trips.Models.Trip;
import com.toyhe.app.Trips.Models.TripStatus;
import com.toyhe.app.Trips.Models.TripType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record TripResponse(
        long tripID,
        String tripName,
        LocalDateTime departureDateTime,
        LocalDateTime arrivalDateTime,
        Days days,
        long boatID ,
        List<BoatClassResponse> boatClasses,
        TripType type,
        double price,
        TripStatus status
) {
    public static TripResponse fromTrip(Trip trip) {
        return new TripResponse(
                trip.getTripID(),
                trip.getTripName(),
                trip.getDepartureDateTime(),
                trip.getArrivalDateTime(),
                trip.getDays(),
                trip.getBoat().getBoatID(),
                trip.getBoatClasses().stream()
                        .map(BoatClassResponse::fromBoatClassToDTO)
                        .collect(Collectors.toList()),
                trip.getType(),
                trip.getPrice(),
                trip.getStatus()
        );
    }
}

