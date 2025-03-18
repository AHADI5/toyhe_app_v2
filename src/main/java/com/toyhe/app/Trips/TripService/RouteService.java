package com.toyhe.app.Trips.TripService;

import com.toyhe.app.Trips.Dtos.Route.RouteRegisterRequest;
import com.toyhe.app.Trips.Dtos.Route.RouteResponse;
import com.toyhe.app.Trips.Models.Route;
import com.toyhe.app.Trips.Reposiory.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public record RouteService(
        RouteRepository routeRepository
) {
    public RouteResponse registerRoute(RouteRegisterRequest routeRegisterRequest) {
        Route route = Route.builder()
                .routeName(routeRegisterRequest.origin() + "-" + routeRegisterRequest.destination())
                .origin(routeRegisterRequest.origin())
                .destination(routeRegisterRequest.destination())
                .build();
        return RouteResponse.fromEntity(routeRepository.save(route));

    }

    public RouteResponse getRoute(int routeId) {
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new RuntimeException("Route not found"));
        return RouteResponse.fromEntity(route);
    }




}
