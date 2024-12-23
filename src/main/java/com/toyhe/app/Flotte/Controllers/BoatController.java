package com.toyhe.app.Flotte.Controllers;

import com.toyhe.app.Flotte.Dtos.Boat.BoatRegisterRequest;
import com.toyhe.app.Flotte.Dtos.Boat.BoatRegisterResponse;
import com.toyhe.app.Flotte.Services.BoatClassService;
import com.toyhe.app.Flotte.Services.BoatService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/boat")
public record BoatController(
        BoatService boatService
) {
    static String modelName  = "BOAT"  ;
    @PostMapping("/register")
    public ResponseEntity<BoatRegisterResponse> registerBoat(
            @RequestBody BoatRegisterRequest boatRegisterRequest ,
            HttpServletRequest httpServletRequest) {
        return  boatService.registerBoat(boatRegisterRequest) ;

    }

}
