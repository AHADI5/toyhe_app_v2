package com.toyhe.app.Flotte.Services;

import com.toyhe.app.Flotte.Dtos.Boat.BoatRegisterRequest;
import com.toyhe.app.Flotte.Dtos.Boat.BoatRegisterResponse;
import com.toyhe.app.Flotte.Dtos.Boat.BoatResponse;
import com.toyhe.app.Flotte.Dtos.BoatClasses.BoatClassRegisterRequest;
import com.toyhe.app.Flotte.Models.Boat;
import com.toyhe.app.Flotte.Models.BoatClass;
import com.toyhe.app.Flotte.Repositories.BoatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public record BoatService(
        BoatRepository boatRepository  ,
        BoatClassService boatClassService

) {
    public ResponseEntity<BoatRegisterResponse> registerBoat(BoatRegisterRequest boatRegisterRequest) {

        Boat  boat  = Boat.builder()
                .name(boatRegisterRequest.name())
                .registeredDate(new Date())
                .fabricationDate(boatRegisterRequest.fabricationDate())
                .supportedWeight(boatRegisterRequest.supportedWeight())
                .boatWeight(boatRegisterRequest.boatWeight())
                .boatCategory(boatRegisterRequest.boatCategory())
                .abbreviation(generateAbbreviation(boatRegisterRequest.name()))
                .build();

        boat = boatRepository.save(boat);
        //Registering Boat Classes
        //Constructing the Request by injecting the realID in it
        List <BoatClassRegisterRequest> boatClassRegisterLists  =  new ArrayList<>();
        //TODO : COMPUTE THE CLASS PRICE FROM THE PRICE LIST SET
        for (BoatClassRegisterRequest boatClass : boatRegisterRequest.boatClassList())  {
            boatClassRegisterLists.add(
                    new BoatClassRegisterRequest(
                            boatClass.name()  ,
                            boatClass.placesNumber()  ,
                            boat.getBoatID() ,
                            boatClass.priceListID()  ,
                            boatClass.productID()
                    )
            ) ;
        }

        List<BoatClass> boatClassList =  boatClassService.registerBoatClass(boatClassRegisterLists).getBody() ;
        boat.setBoatClasses(boatClassList);
        boat = boatRepository.save(boat);
        return (ResponseEntity.ok(BoatRegisterResponse.fromBoatToResponseDTO(boat))) ;
    }

    //Helper methods
    /**
     * Generates an abbreviation from a word based on its length:
     * - If the word has exactly 4 letters, return the first 3 letters.
     * - If the word has more than 4 letters, return the first 4 letters.
     * - If the word has 2 or fewer letters, return it as is.
     *
     * @param word The input word to abbreviate.
     * @return The abbreviation of the word.
     */
    public String generateAbbreviation(String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Input word cannot be null or empty.");
        }

        int length = word.length();
        if (length <= 2) {
            return word;
        } else if (length == 4) {
            return word.substring(0, 3).toUpperCase();
        } else {
            return word.substring(0, 4).toUpperCase();
        }
    }


    public ResponseEntity<List<BoatResponse>> getAllBoats() {
        List<Boat> boats = boatRepository.findAll();
        List<BoatResponse> boatRegisterResponses = new ArrayList<>();
        for (Boat boat : boats) {
            BoatRegisterResponse.fromBoatToResponseDTO(boat);
            boatRegisterResponses.add(BoatResponse.fromBoat(boat));
        }

        return ResponseEntity.ok(boatRegisterResponses);
    }

    public ResponseEntity<BoatResponse> getBoatByID(long boatID) {
        Optional<Boat> boat = boatRepository.findById(boatID) ;

        if(boat.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(BoatResponse.fromBoat(boat.get()));

    }

    public ResponseEntity<BoatResponse> updateBoat(long boatID, BoatRegisterRequest newBoat) {
        Optional<Boat> boat = boatRepository.findById(boatID);
        if(boat.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Boat existingBoat = boat.get();
        existingBoat.setName(newBoat.name());
        existingBoat.setAbbreviation(generateAbbreviation(newBoat.name()));
        existingBoat.setNumMatriculation(newBoat.numMatriculation());
        existingBoat.setMotorType(newBoat.motorType());
        existingBoat.setFabricationDate(newBoat.fabricationDate());
        existingBoat.setBoatCategory(newBoat.boatCategory());
        existingBoat.setBoatWeight(newBoat.boatWeight());
        existingBoat.setSupportedWeight(newBoat.supportedWeight());

        Boat updatedBoat = boatRepository.save(existingBoat);

        return ResponseEntity.ok(BoatResponse.fromBoat(updatedBoat));


    }

    public boolean isDataBaseEmpty() {
        return boatRepository.count() == 0;
    }
}
