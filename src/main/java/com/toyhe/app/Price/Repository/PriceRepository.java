package com.toyhe.app.Price.Repository;

import com.toyhe.app.Price.Model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
