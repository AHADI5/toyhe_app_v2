package com.toyhe.app.Tickets.Repository;

import com.toyhe.app.Tickets.Model.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Integer> {
}
