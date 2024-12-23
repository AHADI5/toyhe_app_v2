package com.toyhe.app.Flotte.Models;

import com.toyhe.app.Trips.Models.Trip;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Boat {
    @Id
    private Long boatID;
    private Date registeredDate;
    private String name;
    private String abbreviation  ;
    private String numMatriculation  ;
    private String motorType  ;
    private String fabricationDate  ;
    private String boatCategory ;
    private double boatWeight;
    private double supportedWeight ;
    private int totalPlaces  ;

    @OneToMany(mappedBy = "boat")
    private List<BoatClass> boatClasses ;
    @OneToMany(mappedBy = "boat")
    private  List<Trip> trips ;
}
