package com.toyhe.app.Tickets.Model;

import com.toyhe.app.Customer.Models.Customer;
import com.toyhe.app.Flotte.Models.Boat;
import com.toyhe.app.Flotte.Models.BoatClass;
import com.toyhe.app.Trips.Models.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue
    long ticketId;
    String reference  ;
    LocalDateTime reservationDate  ;
    String operator ;
    @ManyToOne
    Trip trip  ;
    String description;
    @ManyToOne
    @JoinColumn(name = "boat_id")
    Boat boat  ;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer ;
    @ManyToOne
    BoatClass boatClass ;
    Double price ;
    @OneToMany(mappedBy = "ticket")
    List<Goods> goods ;


}
