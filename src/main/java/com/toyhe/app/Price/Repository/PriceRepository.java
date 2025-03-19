package com.toyhe.app.Price.Repository;

import com.toyhe.app.Price.Model.PriceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<PriceModel, Long> {
}
