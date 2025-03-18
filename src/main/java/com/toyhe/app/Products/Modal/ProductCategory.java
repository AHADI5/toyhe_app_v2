package com.toyhe.app.Products.Modal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class ProductCategory {
    @Id
    private Long productTypeId;
    private String productTypeName;
    private String productTypeDescription;
    @OneToMany(mappedBy = "product_type_id")
    private List<Product> products;
}
