package com.toyhe.app.Trips.Reposiory;

import com.toyhe.app.Trips.Models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
