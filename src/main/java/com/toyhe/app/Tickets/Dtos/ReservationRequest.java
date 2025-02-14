package com.toyhe.app.Tickets.Dtos;

public record ReservationRequest(
        long userAccountID  ,
        String email  ,
        String firstName ,
        String lastName ,
        String telephone ,
        String operator ,
        long tripID  ,
        long classID ,
        String note
) {
}
