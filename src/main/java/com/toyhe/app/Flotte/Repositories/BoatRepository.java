package com.toyhe.app.Flotte.Repositories;

import com.toyhe.app.Flotte.Models.Boat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoatRepository extends JpaRepository<Boat, Long> {
}
