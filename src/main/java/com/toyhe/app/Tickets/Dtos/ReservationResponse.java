package com.toyhe.app.Tickets.Dtos;

import com.toyhe.app.Flotte.Dtos.BoatClasses.BoatClassResponse;
import com.toyhe.app.Tickets.Model.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ReservationResponse(
        String customerName ,
        String customerEmail,
        LocalDateTime reservationDate  ,
        LocalDateTime travelDate ,
        String route  ,
        String boatName  ,
        String className  ,
        double price ,
        Operator operator ,
        List<GoodsRegisterResponse> goods
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
                ticket.getBoatClass().getPriceModel() == null ? 0 : ticket.getBoatClass().getPriceModel().getAmount() ,
                operatorResponse.fromEmail(ticket.getOperator()) ,
                ticket.getGoods() == null ?  null  : ticket.getGoods().stream()
                        .map(GoodsRegisterResponse::fromEntity)
                        .collect(Collectors.toList())
        ) ;
    }

}
