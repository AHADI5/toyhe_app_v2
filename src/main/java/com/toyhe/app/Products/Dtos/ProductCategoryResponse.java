package com.toyhe.app.Products.Dtos;

import com.toyhe.app.Products.Modal.ProductCategorization;

import java.util.List;
import java.util.stream.Collectors;

public record ProductCategoryResponse(
        Long productTypeId,
        String productTypeName,
        String productTypeDescription,
        List<ProductResponse> products
) {
    public static ProductCategoryResponse fromModel(ProductCategorization category) {
        return new ProductCategoryResponse(
                category.getProductTypeId(),
                category.getProductTypeName(),
                category.getProductTypeDescription(),
                category.getProducts() != null
                        ? category.getProducts().stream()
                        .map(ProductResponse::fromModel) // Convert each Products to ProductResponse
                        .collect(Collectors.toList())
                        : List.of() // Return an empty list if there are no products
        );
    }
}

