package com.toyhe.app.Flotte.Services;

import com.toyhe.app.Flotte.Dtos.BoatClasses.BoatClassRegisterRequest;
import com.toyhe.app.Flotte.Models.Boat;
import com.toyhe.app.Flotte.Models.BoatClass;
import com.toyhe.app.Flotte.Repositories.BoatClassRepository;
import com.toyhe.app.Flotte.Repositories.BoatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public record BoatClassService(
        BoatClassRepository boatClassRepository,
        BoatRepository boatRepository
) {
    public ResponseEntity<List<BoatClass>> registerBoatClass(List<BoatClassRegisterRequest> requests) {
        List<BoatClass> boatClasses = new ArrayList<>();

        for (BoatClassRegisterRequest boatClassRegisterRequest : requests) {
            // Find the boat corresponding to the ID
            Optional<Boat> optionalBoat = boatRepository.findById(boatClassRegisterRequest.boatID());

            if (optionalBoat.isEmpty()) {
                // If the boat does not exist, return an error response
                return ResponseEntity.badRequest().body(
                        List.of()
                );
            }

            // Create and add a new BoatClass object
            BoatClass boatClass = BoatClass.builder()
                    .boat(optionalBoat.get())
                    .name(boatClassRegisterRequest.name())
                    .placesNumber(boatClassRegisterRequest.placesNumber())
                    .build();

            boatClasses.add(boatClass);
        }

        // Save all BoatClass objects
        List<BoatClass> savedBoatClasses = boatClassRepository.saveAll(boatClasses);

        // Return the list of registered BoatClass objects
        return ResponseEntity.ok(savedBoatClasses);
    }
}
