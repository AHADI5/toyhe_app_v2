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
    long tripID ;
    String tripName ;
    LocalDateTime departureDateTime  ;
    LocalDateTime arrivalDateTime ;
    Days days ;
    @ManyToOne
    Boat boat ;
    @OneToMany
    List<BoatClass> boatClasses ;
    TripType type  ;
    double price ;
    TripStatus status ;
}
