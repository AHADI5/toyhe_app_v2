package com.toyhe.app.Tickets.Dtos;

import com.toyhe.app.Tickets.Model.Goods;

public record GoodsRegisterResponse(
        Long id,
        double weight,
        double volumes,
        String description
) {
    public static GoodsRegisterResponse fromEntity(Goods goods) {
        return new GoodsRegisterResponse(
                goods.getId(),
                goods.getWeight(),
                goods.getVolume(),
                goods.getDescription()
        );
    }
}

