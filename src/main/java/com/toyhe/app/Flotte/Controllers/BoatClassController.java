package com.toyhe.app.Flotte.Controllers;

import com.toyhe.app.Flotte.Dtos.Boat.BoatRegisterRequest;
import com.toyhe.app.Flotte.Dtos.BoatClasses.BoatClassRegisterRequest;
import com.toyhe.app.Flotte.Dtos.BoatClasses.BoatClassResponse;
import com.toyhe.app.Flotte.Models.BoatClass;
import com.toyhe.app.Flotte.Services.BoatClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/boatClass")
public record BoatClassController(
        BoatClassService boatClassService
) {
    @PostMapping("{boatID}/newClass")
    public ResponseEntity<BoatClassResponse> newClass(
            @PathVariable int boatID ,
            @RequestBody BoatClassRegisterRequest boatClassRegisterRequest) {
        return boatClassService.addBoatClassToABoat(boatID, boatClassRegisterRequest) ;
    }
    @GetMapping("/{boatID}")
    public ResponseEntity<List<BoatClassResponse>> getBoatClasses(@PathVariable long boatID) {
        return boatClassService.getBoatClasses(boatID) ;
    }

    @PutMapping("/{boatClassID}")
    public ResponseEntity<BoatClassResponse> updateBoatClass(
            @RequestBody BoatClassRegisterRequest boatClassRegisterRequest,
            @PathVariable long boatClassID) {
        return  boatClassService.updateClassBoat(boatClassID , boatClassRegisterRequest) ;

    }
}
