package com.toyhe.app.Products.Modal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

    @Id
    private Long productId;
    private String productName;
    private String productDescription;
    private String productType ;
    @ManyToOne
    private ProductCategory productCategory;
}
