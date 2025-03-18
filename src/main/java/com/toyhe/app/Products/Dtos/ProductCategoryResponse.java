package com.toyhe.app.Products.Dtos;

import com.toyhe.app.Products.Modal.ProductCategory;

import java.util.List;
import java.util.stream.Collectors;

public record ProductCategoryResponse(
        Long productTypeId,
        String productTypeName,
        String productTypeDescription,
        List<ProductResponse> products
) {
    public static ProductCategoryResponse fromModel(ProductCategory category) {
        return new ProductCategoryResponse(
                category.getProductTypeId(),
                category.getProductTypeName(),
                category.getProductTypeDescription(),
                category.getProducts() != null
                        ? category.getProducts().stream()
                        .map(ProductResponse::fromModel) // Convert each Product to ProductResponse
                        .collect(Collectors.toList())
                        : List.of() // Return an empty list if there are no products
        );
    }
}

