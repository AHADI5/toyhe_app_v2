package com.toyhe.app.Tickets.Services;

import com.toyhe.app.Auth.Model.User;
import com.toyhe.app.Auth.Repositories.UserRepository;
import com.toyhe.app.Customer.Models.Customer;
import com.toyhe.app.Customer.Services.CustomerService;
import com.toyhe.app.Flotte.Models.BoatClass;
import com.toyhe.app.Flotte.Services.BoatClassService;
import com.toyhe.app.Tickets.Dtos.OperatorResponse;
import com.toyhe.app.Tickets.Dtos.ReservationRequest;
import com.toyhe.app.Tickets.Dtos.ReservationResponse;
import com.toyhe.app.Tickets.Model.Ticket;
import com.toyhe.app.Tickets.Repository.TicketRepository;
import com.toyhe.app.Trips.Models.Trip;
import com.toyhe.app.Trips.Reposiory.TripRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public record TicketService(
        CustomerService customerService,
        BoatClassService boatClassService,
        TicketRepository ticketRepository,
        UserRepository userRepository,
        TripRepository tripRepository ,
        OperatorResponse operatorResponse
) {

    public ResponseEntity<ReservationResponse> ticketReservation(ReservationRequest request) {


        Trip trip = tripRepository.findById(request.tripID())
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (trip.getAvailableSeats() == 0) {
            return ResponseEntity.badRequest().body(ReservationResponse.toDto(new Ticket() , operatorResponse));
        }

        Customer customer = customerService.getCustomerByCustomerEmail(request.email());
        if (customer == null) {
            customer = createNewCustomer(request);
        }

        Ticket ticket = createTicket(customer, trip, request);
        return ResponseEntity.ok(ReservationResponse.toDto(ticket ,operatorResponse));
    }

    private Customer createNewCustomer(ReservationRequest request) {
        Customer newCustomer = Customer.builder()
                .customerName(request.firstName() + " " + request.lastName())
                .customerEmail(request.email())
                .phoneNumber(request.telephone())
                .build();
        return customerService.customerRepository().save(newCustomer);
    }


    private Ticket createTicket(Customer customer, Trip trip, ReservationRequest request) {
        BoatClass boatClass = trip.getBoatClasses().stream()
                .filter(bc -> bc.getBoatClassID() == request.classID())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Boat class not found"));

        Ticket ticket = Ticket.builder()
                .customer(customer)
                .operator(request.operator())
                .trip(trip)
                .boat(trip.getBoat())
                .boatClass(boatClass)
                .price(boatClass.getPrice().getAmount())
                .description("Reservation for trip " + trip.getDepartureDateTime())
                .reference("REF" + System.currentTimeMillis())
                .reservationDate(LocalDateTime.now())
                .build();

        ticket = ticketRepository.save(ticket);
        boatClassService.updateClassSeat(boatClass);
        return ticket;
    }
}
