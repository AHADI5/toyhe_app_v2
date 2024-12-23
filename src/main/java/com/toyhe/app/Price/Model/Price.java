package com.toyhe.app.Price.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    @Id
    long priceID  ;
    String priceName  ;
    double pricePrice ;
    LocalDateTime startDate  ;
    LocalDateTime endDate  ;
    PriceType priceType  ;
}
