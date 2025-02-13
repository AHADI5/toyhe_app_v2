package com.toyhe.app.Trips.Models;

import com.toyhe.app.Flotte.Models.Boat;
import com.toyhe.app.Flotte.Models.BoatClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue
    @Column(name = "trip_id")
    long tripID ;
    String tripName ;
    LocalDateTime departureDateTime  ;
    LocalDateTime arrivalDateTime ;
    Days days ;
    String origin , destination ;
    @ManyToOne
    Boat boat ;
    @ManyToMany
    @JoinTable(
            name = "trip_boat_classes",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "boat_classid")
    )
    private List<BoatClass> boatClasses;

    @ManyToOne
    Route route ;

    TripType type  ;
    double price ;
    TripStatus status ;
    int tag  ;
    int expectedComeBackInHours ;
}
