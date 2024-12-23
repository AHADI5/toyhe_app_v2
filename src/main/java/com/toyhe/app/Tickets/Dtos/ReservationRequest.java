package com.toyhe.app.Tickets.Dtos;

public record ReservationRequest(
        long userAccountID  ,
        String email  ,
        long tripID
) {
}
