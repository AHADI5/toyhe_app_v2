package com.toyhe.app.Tickets.Controllers;

import com.toyhe.app.Tickets.Dtos.ReservationRequest;
import com.toyhe.app.Tickets.Dtos.ReservationResponse;
import com.toyhe.app.Tickets.Services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ticket")
public record ReservationController(
        TicketService ticketService

) {
    @PostMapping("/")
    public ResponseEntity<ReservationResponse> reserveTicket(@RequestBody ReservationRequest reservationRequest) {
        return ticketService.ticketReservation(reservationRequest) ;
    }
}
