package com.toyhe.app.Tickets.Services;

import com.toyhe.app.Tickets.Model.Goods;
import com.toyhe.app.Tickets.Repository.GoodsRepository;
import org.springframework.stereotype.Service;

@Service
public record GoodsService(GoodsRepository goodsRepository) {
    public Goods registerGoods () {

    }

}
