package com.toyhe.app.Price.Service;

import com.toyhe.app.Price.Dtos.PriceRequest;
import com.toyhe.app.Price.Dtos.PriceResponse;
import com.toyhe.app.Price.Model.Price;
import com.toyhe.app.Price.Repository.PriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record PriceService(
        PriceRepository priceRepository
) {
    public Price createPrice(PriceRequest priceRequest) {
        Price price = Price.builder()
                .amount(priceRequest.amount())
                .isDefault(priceRequest.isDefault())
                .priceName("" + priceRequest.amount())
                .startDate(priceRequest.startDate())
                .endDate(priceRequest.endDate())
                .build();
        return priceRepository.save(price);
    }
}
