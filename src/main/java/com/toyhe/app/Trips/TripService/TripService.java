package com.toyhe.app.Trips.TripService;

import com.toyhe.app.Flotte.Models.Boat;
import com.toyhe.app.Flotte.Models.BoatClass;
import com.toyhe.app.Flotte.Repositories.BoatClassRepository;
import com.toyhe.app.Flotte.Repositories.BoatRepository;
import com.toyhe.app.Trips.Dtos.TripRegisterRequest;
import com.toyhe.app.Trips.Dtos.TripResponse;
import com.toyhe.app.Trips.Models.Route;
import com.toyhe.app.Trips.Models.Trip;
import com.toyhe.app.Trips.Models.TripStatus;
import com.toyhe.app.Trips.Models.TripType;
import com.toyhe.app.Trips.Reposiory.RouteRepository;
import com.toyhe.app.Trips.Reposiory.TripRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public record TripService(
        BoatRepository boatRepository,
        BoatClassRepository boatClassRepository ,
        TripRepository tripRepository ,
        RouteRepository routeRepository
) {

    /**
     * Registers a new trip and automatically creates recurring trips until the end date.
     */
    public ResponseEntity<List<TripResponse>> registerTrip(TripRegisterRequest request) {
        String tripName = generateUniqueTripName(request.departureDate());
        TripType tripType = determineTripType(request.tripType());

        LocalDateTime startDate = request.departureDate();
        LocalDateTime endDate = calculateEndDate(tripType, startDate, request.durationInWeeks());

        Boat boat = boatRepository.findById(request.boatID())
                .orElseThrow(() -> new IllegalArgumentException("Boat not found with ID: " + request.boatID()));

        List<BoatClass> boatClasses = request.classIDs().stream()
                .map(id -> boatClassRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("BoatClass not found with ID: " + id)))
                .collect(Collectors.toList());


        Route route = routeRepository.findById((int) request.routeID())
                .orElseThrow(() -> new EntityNotFoundException("Route Not Found with ID: " + request.routeID()));

        // Register the first trip and all recurring trips immediately
        return createAndScheduleRecurringTrips(tripName, startDate, request.arrivalDate(), boat, boatClasses, tripType, endDate,
                request.tag(), request.expectedComeBackInHours(), route, request.durationInWeeks());
    }


    /**
     * Creates the initial trip and all recurring trips until the end date.
     */
    private ResponseEntity<List<TripResponse>> createAndScheduleRecurringTrips(String tripName, LocalDateTime startDate, LocalDateTime arrivalDate,
                                                               Boat boat, List<BoatClass> boatClasses, TripType tripType, LocalDateTime endDate ,
                                                               int tag , int expectedComeBackInHours , Route route , int durationInWeeks) {
        //this will store all trips created
        List<TripResponse> responses = new ArrayList<>();
        List<Trip> trips = new ArrayList<>();

        // Calculate the comeback date
        LocalDateTime comeBackDate = startDate.plusDays(expectedComeBackInHours);

        // Determine the departure and comeback days of the week
        DayOfWeek departureDay = startDate.getDayOfWeek();
        DayOfWeek comebackDay = comeBackDate.getDayOfWeek();

        // Determine until when this logic is valid
        LocalDateTime endTimeTable = startDate.plusWeeks(durationInWeeks);

        // Loop to create trips weekly
        LocalDateTime nextTripDate = startDate;

        while (nextTripDate.isBefore(endTimeTable) || nextTripDate.isEqual(endTimeTable)) {
            LocalDateTime nextComebackDate = nextTripDate.plusDays(expectedComeBackInHours);

            // Create the trip
            Trip trip = Trip.builder()
                    .tripName(generateUniqueTripName(nextTripDate))
                    .departureDateTime(nextTripDate)
                    .arrivalDateTime(arrivalDate)
                    .boat(boat)
                    .availableSeats(0)
                    .route(route)
                    .origin(route.getOrigin())
                    .destination(route.getDestination())
                    .boatClasses(boatClasses)
                    .type(tripType)
                    .price(0)
                    .status(TripStatus.SCHEDULED)
                    .tag(tag)
                    .expectedComeBackInHours(expectedComeBackInHours)
                    .build();
            trip.setAvailableSeats(computeAvailableSet(trip));
            Trip savedTrip = tripRepository.save(trip);
            trips.add(savedTrip);

            // Create the comeback trip
            Trip comebackTrip = Trip.builder()
                    .tripName(generateUniqueTripName(nextComebackDate))
                    .departureDateTime(nextComebackDate)
                    .arrivalDateTime(arrivalDate)
                    .availableSeats(0)
                    .boat(boat)
                    .boatClasses(boatClasses)
                    .type(tripType)
                    .price(0)
                    .origin(route.getDestination())
                    .destination(route.getOrigin())
                    .status(TripStatus.SCHEDULED)
                    .tag((int) savedTrip.getTripID()) // Link comeback to the original trip
                    .expectedComeBackInHours(0)
                    .build();
            trip.setAvailableSeats(computeAvailableSet(trip));
            tripRepository.save(comebackTrip);

            trips.add(comebackTrip);

            // Move to the next week
            nextTripDate = nextTripDate.plusWeeks(1);
        }

        for (Trip trip : trips) {
           responses.add( TripResponse.fromTrip(trip)) ;
        }

        return ResponseEntity.ok(responses);

    }



    /**
     * Calculates the end date based on the trip type.
     */
    private LocalDateTime calculateEndDate(TripType tripType, LocalDateTime startDate, int durationInWeeks) {
        if (tripType == TripType.PREPLANNED) {
            return startDate.plusWeeks(durationInWeeks);
        }
        return startDate;
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
        return (hour < 12) ? "Morning" : (hour < 18) ? "Afternoon" : (hour < 21) ? "Evening" : "Night";
    }

    /**
     * Determines the trip type based on the provided trip type string.
     */
    private TripType determineTripType(String tripType) {
        return "preplanned".equalsIgnoreCase(tripType) ? TripType.PREPLANNED : TripType.NOT_PREPLANNED;
    }

    private int  computeAvailableSet(Trip trip) {
        int availableSet = trip.getAvailableSeats();
        List<BoatClass> boatClasses = trip.getBoatClasses();
        for (BoatClass boatClass : boatClasses) {
            availableSet += (int) boatClass.getPlacesNumber();
        }

        return availableSet;

    }

    public void updateTripSeats(Trip trip) {
        trip.setAvailableSeats(trip.getAvailableSeats() -1);
        tripRepository.save(trip);
    }


    public ResponseEntity<List<TripResponse>> getTrips() {
        List<TripResponse> tripResponses = new ArrayList<>();
        List<Trip> trips  = tripRepository.findAll() ;

        for (Trip trip : trips) {
            tripResponses.add(TripResponse.fromTrip(trip));
        }

        return ResponseEntity.ok(tripResponses);

    }

    public boolean isDataBaseEmpty() {
        return  tripRepository.count() == 0;
    }
}
