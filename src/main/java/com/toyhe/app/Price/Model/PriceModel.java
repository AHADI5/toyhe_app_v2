package com.toyhe.app.Price.Model;

import com.toyhe.app.Products.Modal.Products;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceModel {
    @Id
    @GeneratedValue
    long priceID ;
    String priceName  ;
    String deviseName  ;
    double amount ;
    LocalDateTime startDate  ;
    LocalDateTime endDate  ;
    Boolean  isDefault ;
    @OneToMany (mappedBy = "productPriceModel")
    List<Products> products ;
}
