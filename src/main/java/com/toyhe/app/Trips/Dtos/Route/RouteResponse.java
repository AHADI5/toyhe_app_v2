package com.toyhe.app.Trips.Dtos.Route;

import com.toyhe.app.Trips.Models.Route;

public record RouteResponse(
        String routeName,
        String origin,
        String destination
) {
    public static RouteResponse fromEntity(Route route) {
        return new RouteResponse(route.getOrigin() + "-" +route.getDestination(), route.getOrigin(), route.getDestination());
    }
}
