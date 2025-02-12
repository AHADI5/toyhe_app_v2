package com.toyhe.app.Flotte.Dtos.Boat;
import com.toyhe.app.Flotte.Dtos.BoatClasses.BoatClassResponse;
import com.toyhe.app.Flotte.Models.Boat;
import com.toyhe.app.Trips.Dtos.TripResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record BoatResponse(
        long boatID,
        Date registeredDate,
        String name,
        String abbreviation,
        String numMatriculation,
        String motorType,
        String fabricationDate,
        String boatCategory,
        double boatWeight,
        double supportedWeight,
        int totalPlaces,
        List<BoatClassResponse> boatClasses,
        List<TripResponse> trips
) {
    public static BoatResponse fromBoat(Boat boat) {
        return new BoatResponse(
                boat.getBoatID(),
                boat.getRegisteredDate(),
                boat.getName(),
                boat.getAbbreviation(),
                boat.getNumMatriculation(),
                boat.getMotorType(),
                boat.getFabricationDate(),
                boat.getBoatCategory(),
                boat.getBoatWeight(),
                boat.getSupportedWeight(),
                boat.getTotalPlaces(),
                boat.getBoatClasses().stream()
                        .map(BoatClassResponse::fromBoatClassToDTO)
                        .collect(Collectors.toList()),
                boat.getTrips().stream()
                        .map(TripResponse::fromTrip)
                        .collect(Collectors.toList())
        );
    }
}