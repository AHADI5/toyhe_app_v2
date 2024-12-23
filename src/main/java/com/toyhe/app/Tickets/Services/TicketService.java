package com.toyhe.app.Tickets.Services;

import com.toyhe.app.Auth.Model.User;
import com.toyhe.app.Auth.Repositories.UserRepository;
import com.toyhe.app.Customer.Models.Customer;
import com.toyhe.app.Customer.Repository.CustomerRepository;
import com.toyhe.app.Tickets.Dtos.ReservationRequest;
import com.toyhe.app.Tickets.Dtos.ReservationResponse;
import com.toyhe.app.Tickets.Model.Ticket;
import com.toyhe.app.Tickets.Repository.TicketRepository;
import com.toyhe.app.Trips.Models.Trip;
import com.toyhe.app.Trips.Reposiory.TripRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public record TicketService(
        CustomerRepository customerRepository,
        TicketRepository ticketRepository,
        UserRepository userRepository,
        TripRepository tripRepository
) {

    public ResponseEntity<ReservationResponse> ticketReservation(ReservationRequest request) {
        // Find the trip by ID
        Trip trip = tripRepository.findById(request.tripID()).orElse(null);
        if (trip == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Find the customer by user account ID
        Customer customer = customerRepository.findByUserAccountID(request.userAccountID());
        if (customer == null) {
            // If customer is not found, create a new one linked to the user
            Optional<User> user = userRepository.findByEmail(request.email());
            if (user.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            customer = Customer.builder()
                    .customerName(user.get().getUsername()) // Assuming User has a `getName` method
                    .customerEmail(user.get().getEmail())
                    .build();

            // Save the new customer
            customer = customerRepository.save(customer);
        }

        // Create the ticket
        Ticket ticket = Ticket.builder()
                .customer(customer)
                .trip(trip)
                .boat(trip.getBoat())
                .description("Reservation for trip " + trip.getDepartureDateTime())
                .reference("REF" + System.currentTimeMillis()) // Generate a unique reference
                .reservationDate(LocalDateTime.now())

                .build();

        // Save the ticket
        ticket = ticketRepository.save(ticket);

        // Create the response
        ReservationResponse response = new ReservationResponse(
                ticket.getCustomer().getCustomerName(),
                ticket.getReservationDate(),
                ticket.getTrip().getDepartureDateTime(),
                ticket.getBoat().getName(),
                ticket.getTrip().getBoatClass(),
                ticket.getTrip().getPrice()
        );

        return ResponseEntity.ok(response);
    }
}
