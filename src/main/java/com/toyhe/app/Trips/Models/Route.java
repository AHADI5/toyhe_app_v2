package com.toyhe.app.Trips.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long routeId;
    String routeName;
    String origin;
    String destination;
    @OneToMany(mappedBy = "route")
    List<Trip> trips ;
}
