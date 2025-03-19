package com.toyhe.app.Flotte.Models;

import com.toyhe.app.Price.Model.PriceModel;
import com.toyhe.app.Products.Modal.Products;
import com.toyhe.app.Tickets.Model.Ticket;
import com.toyhe.app.Trips.Models.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoatClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boat_classid")
    private Long boatClassID;

    private String name;
    private int placesNumber;

    @ManyToOne
    @JoinColumn(name = "boat_id")
    private Boat boat;
    @ManyToOne
    private PriceModel priceModel;
    @ManyToMany(mappedBy = "boatClasses")
    private List<Trip> trips;
    @OneToMany(mappedBy = "boatClass")
    private List<Ticket> tickets;
    @OneToMany (mappedBy = "boatClass")
    private List<Products> products  ;
}
