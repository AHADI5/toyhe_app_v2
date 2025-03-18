package com.toyhe.app.Price.Controllers;

import com.toyhe.app.Price.Dtos.PriceRequest;
import com.toyhe.app.Price.Dtos.PriceResponse;
import com.toyhe.app.Price.Service.PriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/price")
public record PriceController(
        PriceService priceService
) {
    @PostMapping("/")
    public ResponseEntity<PriceResponse> createPrice(
            @RequestBody PriceRequest priceRequest
    ) {
        return ResponseEntity.ok(PriceResponse.toPriceResponse(priceService.createPrice(priceRequest)));
    }
}
