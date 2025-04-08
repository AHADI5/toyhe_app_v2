package com.toyhe.app.Tickets.Services;

import com.toyhe.app.Auth.Repositories.UserRepository;
import com.toyhe.app.Customer.Models.Customer;
import com.toyhe.app.Customer.Models.CustomerType;
import com.toyhe.app.Customer.Services.CustomerService;
import com.toyhe.app.Flotte.Models.BoatClass;
import com.toyhe.app.Flotte.Repositories.BoatClassRepository;
import com.toyhe.app.Flotte.Services.BoatClassService;
import com.toyhe.app.Tickets.Dtos.GoodsRegisterRequest;
import com.toyhe.app.Tickets.Dtos.OperatorResponse;
import com.toyhe.app.Tickets.Dtos.ReservationRequest;
import com.toyhe.app.Tickets.Dtos.ReservationResponse;
import com.toyhe.app.Tickets.Model.Goods;
import com.toyhe.app.Tickets.Model.Ticket;
import com.toyhe.app.Tickets.Repository.GoodsRepository;
import com.toyhe.app.Tickets.Repository.TicketRepository;
import com.toyhe.app.Trips.Models.Trip;
import com.toyhe.app.Trips.Reposiory.TripRepository;
import com.toyhe.app.Trips.TripService.TripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public record TicketService(
        CustomerService customerService,
        BoatClassService boatClassService,
        TicketRepository ticketRepository,
        UserRepository userRepository,
        TripRepository tripRepository ,
        OperatorResponse operatorResponse ,
        BoatClassRepository boatClassRepository ,
        TripService tripService ,
        GoodsRepository  goodsRepository
) {

    public ResponseEntity<ReservationResponse> ticketReservation(ReservationRequest request) {


        Trip trip = tripRepository.findById(request.tripID())
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        log.info("Available  Seats{}", trip.getAvailableSeats());
        //TODO : GET THE  BOAT CLASS

        Optional<BoatClass> boatClass = boatClassRepository.findById(request.classID())  ;


        if (trip.getAvailableSeats() == 0) {
            return ResponseEntity.badRequest().body(ReservationResponse.toDto(new Ticket() , operatorResponse));
        }

        Optional<Customer> customer = customerService.customerRepository().findById(Integer.valueOf(request.customerId()));
        if (customer.isEmpty()) {
            log.info("Customer not found");

            customer = Optional.of(createNewCustomer(request));

        }

        Ticket ticket = createTicket(customer.get(), trip, request);
        return ResponseEntity.ok(ReservationResponse.toDto(ticket ,operatorResponse));
    }

    private Customer createNewCustomer(ReservationRequest request) {
        if (request.isCompany()) {
            return  customerService.createCompanyCustomer(request.companyCustomerRegisterRequest(),  CustomerType.CUSTOMER) ;
        } else {
            return customerService.createNonCompanyCustomer(request.nonCompanyCustomerRegisterRequest() , CustomerType.CUSTOMER);
        }

    }

    private List<Goods> registerGoods(List<GoodsRegisterRequest> goods , Ticket ticket) {
        List<Goods> goodsList = new ArrayList<>();
        for (GoodsRegisterRequest goodsRegisterRequest : goods) {
            Goods goodsItem = Goods.builder()
                    .ticket(ticket)
                    .volume(goodsRegisterRequest.volumes())
                    .weight(goodsRegisterRequest.weight())
                    .description(goodsRegisterRequest.description())
                    .build();
            goodsList.add(goodsItem);
        }
        return goodsRepository.saveAll(goodsList);
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
                .price(boatClass.getPriceModel() != null ? boatClass.getPriceModel().getAmount() : 0)
                .description("Reservation for trip " + trip.getDepartureDateTime())
                .reference("REF" + System.currentTimeMillis())
                .reservationDate(LocalDateTime.now())
                .build();

        ticket = ticketRepository.save(ticket);
        //TODO : Register goods on this ticket

        if (request.goodsList() != null) {
            List<Goods> goods = registerGoods(request.goodsList(), ticket);
            ticket.setGoods(goods);
            ticket = ticketRepository.save(ticket);
        }

        tripService.updateTripSeats(trip);
        return ticket;
    }

    public ResponseEntity<List<ReservationResponse>> getTicketsByTeller(String tellerUserName) {
        List<ReservationResponse> reservationResponses  = new ArrayList<>();
        List<Ticket> tickets  = ticketRepository.findTicketsByOperator(tellerUserName);
        for (Ticket  ticket : tickets) {
            reservationResponses.add(ReservationResponse.toDto(ticket , operatorResponse)) ;
        }
        return  reservationResponses.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reservationResponses);
    }

    public boolean isDataBaseEmpty() {
        return ticketRepository.count() == 0 ;
    }
}
