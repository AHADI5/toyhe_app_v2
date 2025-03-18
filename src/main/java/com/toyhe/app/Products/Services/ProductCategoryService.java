package com.toyhe.app.Products.Services;

import com.toyhe.app.Products.Dtos.ProductCategoryRequest;
import com.toyhe.app.Products.Dtos.ProductCategoryResponse;
import com.toyhe.app.Products.Modal.ProductCategory;
import com.toyhe.app.Products.Repository.ProductCategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public record ProductCategoryService(
        ProductCategoryRepository productCategoryRepository
) {
    public ResponseEntity<ProductCategoryResponse> createProductCategory(ProductCategoryRequest productCategoryRequest) {
        ProductCategory  productCategory = ProductCategory.builder()
                .productTypeName(productCategoryRequest.productTypeName())
                .productTypeDescription(productCategoryRequest.productTypeDescription())
                .build();

        return ResponseEntity.ok(ProductCategoryResponse.fromModel(productCategory) ) ;

    }
}
