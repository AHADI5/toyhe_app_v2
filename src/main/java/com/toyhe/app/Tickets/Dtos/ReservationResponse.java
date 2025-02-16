package com.toyhe.app.Tickets.Dtos;

import com.toyhe.app.Flotte.Models.BoatClass;
import com.toyhe.app.Tickets.Model.Ticket;

import java.time.LocalDateTime;

public record ReservationResponse(
        String customerName ,
        String customerEmail,
        LocalDateTime reservationDate  ,
        LocalDateTime travelDate ,
        String route  ,
        String boatName  ,
        String className  ,
        double price ,
        Operator operator
) {
    public static ReservationResponse toDto(Ticket ticket , OperatorResponse operatorResponse) {

        return new ReservationResponse(
                ticket.getCustomer().getCustomerName()  ,
                ticket.getCustomer().getCustomerEmail() ,
                ticket.getTrip().getDepartureDateTime()  ,
                ticket.getTrip().getDepartureDateTime()  ,
                ticket.getTrip().getOrigin() + " " + ticket.getTrip().getDestination(),
                ticket.getTrip().getBoat().getName()  ,
                ticket.getBoatClass().getName()  ,
                ticket.getBoatClass().getBoatClassPrice() ,
                operatorResponse.fromEmail(ticket.getOperator())


        ) ;
    }

}
