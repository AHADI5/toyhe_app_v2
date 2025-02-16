package com.toyhe.app.Tickets.Services;

import com.toyhe.app.Auth.Model.User;
import com.toyhe.app.Auth.Repositories.UserRepository;
import com.toyhe.app.Customer.Models.Customer;
import com.toyhe.app.Customer.Repository.CustomerRepository;
import com.toyhe.app.Customer.Services.CustomerService;
import com.toyhe.app.Flotte.Models.BoatClass;
import com.toyhe.app.Flotte.Services.BoatClassService;
import com.toyhe.app.Tickets.Dtos.ReservationRequest;
import com.toyhe.app.Tickets.Dtos.ReservationResponse;
import com.toyhe.app.Tickets.Model.Operator;
import com.toyhe.app.Tickets.Model.Ticket;
import com.toyhe.app.Tickets.Repository.TicketRepository;
import com.toyhe.app.Trips.Models.Trip;
import com.toyhe.app.Trips.Reposiory.TripRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public record TicketService(
        CustomerService customerService,
        BoatClassService boatClassService,
        TicketRepository ticketRepository,
        UserRepository userRepository,
        TripRepository tripRepository
) {

    public ResponseEntity<ReservationResponse> ticketReservation(ReservationRequest request) {
        Operator operator = determineOperator(request.operator());

        Trip trip = tripRepository.findById(request.tripID())
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (trip.getAvailableSeats() == 0) {
            return ResponseEntity.badRequest().body(ReservationResponse.toDto(new Ticket()));
        }

        Customer customer = customerService.getCustomerByCustomerEmail(request.email());
        if (customer == null && operator == Operator.TELLER) {
            customer = createNewCustomer(request);
        }

        if (customer == null) {
            Optional<User> user = userRepository.findByEmail(request.email());
            if (user.isPresent()) {
                customer = createCustomerFromUser(user.get());
            } else {
                customer = createNewCustomer(request);
            }
        }

        Ticket ticket = createTicket(customer, trip, request);
        return ResponseEntity.ok(ReservationResponse.toDto(ticket));
    }

    private Customer createNewCustomer(ReservationRequest request) {
        Customer newCustomer = Customer.builder()
                .customerName(request.firstName() + " " + request.lastName())
                .customerEmail(request.email())
                .phoneNumber(request.telephone())
                .build();
        return customerService.customerRepository().save(newCustomer);
    }

    private Customer createCustomerFromUser(User user) {
        Customer customer = Customer.builder()
                .customerName(user.getUsername())
                .customerEmail(user.getEmail())
                .build();
        return customerService.customerRepository().save(customer);
    }

    private Ticket createTicket(Customer customer, Trip trip, ReservationRequest request) {
        BoatClass boatClass = trip.getBoatClasses().stream()
                .filter(bc -> bc.getBoatClassID() == request.classID())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Boat class not found"));

        Ticket ticket = Ticket.builder()
                .customer(customer)
                .trip(trip)
                .boat(trip.getBoat())
                .boatClass(boatClass)
                .price(boatClass.getBoatClassPrice())
                .description("Reservation for trip " + trip.getDepartureDateTime())
                .reference("REF" + System.currentTimeMillis())
                .reservationDate(LocalDateTime.now())
                .build();

        ticket = ticketRepository.save(ticket);
        boatClassService.updateClassSeat(boatClass);
        return ticket;
    }

    private void decrementAvailableSeats(Trip trip) {
        trip.setAvailableSeats(trip.getAvailableSeats() - 1);
        tripRepository.save(trip);
    }
    private Operator determineOperator(String operator) {
        return switch (operator) {
            case "teller" -> Operator.TELLER;
            case "user" -> Operator.USER;
            default -> null;
        };
    }
}
