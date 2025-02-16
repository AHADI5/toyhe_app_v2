package com.toyhe.app.Trips.Dtos.Route;

public record RouteRegisterRequest(
        String routeName,
        String origin,
        String destination
)  {
}
