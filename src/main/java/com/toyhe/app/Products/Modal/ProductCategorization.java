package com.toyhe.app.Products.Modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductCategorization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productTypeId;
    private String productTypeName;
    private String productTypeDescription;
    @OneToMany(mappedBy = "productCategorization")
    private List<Products> products;
}
