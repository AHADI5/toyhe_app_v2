package com.toyhe.app.Flotte.Controllers;

import com.toyhe.app.Flotte.Dtos.Boat.BoatRegisterRequest;
import com.toyhe.app.Flotte.Dtos.Boat.BoatRegisterResponse;
import com.toyhe.app.Flotte.Dtos.Boat.BoatResponse;
import com.toyhe.app.Flotte.Models.Boat;
import com.toyhe.app.Flotte.Services.BoatClassService;
import com.toyhe.app.Flotte.Services.BoatService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/boat")
public record BoatController(
        BoatService boatService
) {
    static String modelName  = "BOAT"  ;
    @PostMapping("/")
    public ResponseEntity<BoatRegisterResponse> registerBoat(
            @RequestBody BoatRegisterRequest boatRegisterRequest ,
            HttpServletRequest httpServletRequest) {
        return  boatService.registerBoat(boatRegisterRequest) ;
    }

    @GetMapping("/")
    public ResponseEntity<List<BoatResponse>> getAllBoats(
            HttpServletRequest httpServletRequest
    ) {
        return boatService.getAllBoats() ;

    }

    @GetMapping("/{boatID}/")
    public ResponseEntity<BoatResponse> getBoatById(
            @PathVariable long boatID ,
            HttpServletRequest httpServletRequest
    ) {
        return boatService.getBoatByID(boatID) ;

    }

    @PutMapping("/{boatID}")
    public  ResponseEntity<BoatResponse> updateBoat(
            @PathVariable long boatID ,
            @RequestBody BoatRegisterRequest newBoat
    ) {
        return  boatService.updateBoat(boatID, newBoat) ;

    }


}
