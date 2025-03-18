package com.toyhe.app.Products.Dtos;


import com.toyhe.app.Products.Modal.Product;

public record ProductResponse(
        Long productId,
        String productName,
        String productDescription,
        String productType,
        String productCategoryName
) {
    public static ProductResponse fromModel(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getProductType(),
                product.getProductCategory() != null ? product.getProductCategory().getProductTypeName() : null
        );
    }
}
