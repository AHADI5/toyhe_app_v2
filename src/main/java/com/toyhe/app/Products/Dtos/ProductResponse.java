package com.toyhe.app.Products.Dtos;


import com.toyhe.app.Products.Modal.Products;

public record ProductResponse(
        Long productId,
        String productName,
        String productDescription,
        String productType,
        String productCategoryName
) {
    public static ProductResponse fromModel(Products products) {
        return new ProductResponse(
                products.getProductId(),
                products.getProductName(),
                products.getProductDescription(),
                products.getProductType(),
                products.getProductCategorization() != null ? products.getProductCategorization().getProductTypeName() : null
        );
    }
}
