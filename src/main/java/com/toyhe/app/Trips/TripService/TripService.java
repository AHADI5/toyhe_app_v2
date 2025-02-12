package com.toyhe.app.Trips.TripService;

import com.toyhe.app.Flotte.Models.Boat;
import com.toyhe.app.Flotte.Models.BoatClass;
import com.toyhe.app.Flotte.Repositories.BoatClassRepository;
import com.toyhe.app.Flotte.Repositories.BoatRepository;
import com.toyhe.app.Trips.Dtos.TripRegisterRequest;
import com.toyhe.app.Trips.Models.Trip;
import com.toyhe.app.Trips.Models.TripStatus;
import com.toyhe.app.Trips.Models.TripType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public record TripService(
        BoatRepository boatRepository,
        BoatClassRepository boatClassRepository
) {

    /**
     * Registers a new trip with dynamic properties based on the request.
     */
    public ResponseEntity<String> registerTrip(TripRegisterRequest tripRegisterRequest) {
        // Generate a unique name based on departure time
        String tripName = generateUniqueTripName(tripRegisterRequest.departureDate());

        // Determine trip type and additional properties
        TripType tripType = determineTripType(tripRegisterRequest.tripType());
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        if (tripType == TripType.PREPLANNED) {
            DayOfWeek dayOfWeek = tripRegisterRequest.departureDate().getDayOfWeek();
            startDate = tripRegisterRequest.departureDate();
            endDate = startDate.plusWeeks(4).with(dayOfWeek);
        }

        // Find boat and boat class
        Optional<?> boat = boatRepository.findById(tripRegisterRequest.boatID());
        Optional<?> boatClass = boatClassRepository.findById(tripRegisterRequest.boatClass());

        if (boat.isEmpty() || boatClass.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid boat or boat class ID.");
        }

        // Build and save the trip
        Trip trip = Trip.builder()
                .tripName(tripName)
                .departureDateTime(tripRegisterRequest.departureDate())
                .arrivalDateTime(tripRegisterRequest.arrvialDate())
                .boat((Boat) boat.get())
                .type(tripType)
                .price(0)
                .status(TripStatus.SCHEDULED)
                .build();

        // Save the trip (uncomment and replace with actual repository save method)
        // tripRepository.save(trip);

        return ResponseEntity.ok("Trip registered successfully with name: " + tripName);
    }

    /**
     * Generates a unique name for the trip based on departure time.
     */
    private String generateUniqueTripName(LocalDateTime departureTime) {
        String timeOfDay = determineTimeOfDay(departureTime);
        return "Trip-" + departureTime.toLocalDate() + "-" + timeOfDay + "-" + UUID.randomUUID();
    }

    /**
     * Determines the time of day based on departure time.
     */
    private String determineTimeOfDay(LocalDateTime departureTime) {
        int hour = departureTime.getHour();
        if (hour < 12) return "Morning";
        if (hour < 18) return "Afternoon";
        if (hour < 21) return "Evening";
        return "Night";
    }

    /**
     * Determines the trip type based on the provided trip type string.
     */
    private TripType determineTripType(String tripType) {
        return "preplanned".equalsIgnoreCase(tripType) ? TripType.PREPLANNED : TripType.NOT_PREPLANNED;
    }
}
