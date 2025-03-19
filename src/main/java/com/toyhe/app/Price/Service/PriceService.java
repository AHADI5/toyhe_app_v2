package com.toyhe.app.Price.Service;

import com.toyhe.app.Price.Dtos.PriceRequest;
import com.toyhe.app.Price.Model.PriceModel;
import com.toyhe.app.Price.Repository.PriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record PriceService(
        PriceRepository priceRepository
) {
    public PriceModel createPrice(PriceRequest priceRequest) {
        PriceModel priceModel = PriceModel.builder()
                .amount(priceRequest.amount())
                .deviseName(priceRequest.deviseName())
                .isDefault(priceRequest.isDefault())
                .priceName("" + priceRequest.amount())
                .startDate(priceRequest.startDate())
                .endDate(priceRequest.endDate())
                .build();
        return priceRepository.save(priceModel);
    }

    public boolean isDataBaseEmpty() {
        return  priceRepository.count() == 0 ;
    }
}
