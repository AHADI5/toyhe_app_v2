package com.toyhe.app.Trips.Controllers;

import com.toyhe.app.Trips.Dtos.Route.RouteRegisterRequest;
import com.toyhe.app.Trips.Dtos.Route.RouteResponse;
import com.toyhe.app.Trips.Models.Route;
import com.toyhe.app.Trips.TripService.RouteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/routes")
public record RouteControllers(
        RouteService service
        ) {
    @PostMapping("/")
    public  RouteResponse registerRoute(@RequestBody  RouteRegisterRequest registerRequest) {
        return service.registerRoute(registerRequest);
    }
}
