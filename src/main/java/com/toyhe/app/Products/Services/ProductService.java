package com.toyhe.app.Products.Services;

import com.toyhe.app.Products.Dtos.ProductRequest;
import com.toyhe.app.Products.Dtos.ProductResponse;
import com.toyhe.app.Products.Modal.Product;
import com.toyhe.app.Products.Modal.ProductCategory;
import com.toyhe.app.Products.Repository.ProductCategoryRepository;
import com.toyhe.app.Products.Repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record ProductService(
        ProductRepository productRepository ,
        ProductCategoryRepository  productCategoryRepository
) {
    public ResponseEntity<ProductResponse> createProduct(ProductRequest productRequest) {
        Optional<ProductCategory> productCategory = productCategoryRepository.findById(productRequest.productCategoryId()) ;
        Product product = Product.builder()
                .productName(productRequest.productName())
                .productDescription(productRequest.productDescription())
                .productType(productRequest.productType())
                .productCategory(productCategory.orElse(null))
                .build();

        return ResponseEntity.ok(ProductResponse.fromModel(product)) ;
    }
}
