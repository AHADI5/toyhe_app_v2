package com.toyhe.app.Trips.Controllers;

import com.toyhe.app.Trips.Dtos.TripRegisterRequest;
import com.toyhe.app.Trips.Dtos.TripResponse;
import com.toyhe.app.Trips.TripService.TripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("api/v1/trips")
public record TripsControllers(
        TripService  tripService
) {
    @PostMapping("/")
    public ResponseEntity<List<TripResponse>> createTrip(@RequestBody TripRegisterRequest registerRequest) {
        log.info("Create trip request: {}", registerRequest);
        return  tripService.registerTrip(registerRequest) ;
    }
}
