package com.toyhe.app.Products.Services;

import com.toyhe.app.Price.Model.PriceModel;
import com.toyhe.app.Price.Repository.PriceRepository;
import com.toyhe.app.Products.Dtos.ProductRequest;
import com.toyhe.app.Products.Dtos.ProductResponse;
import com.toyhe.app.Products.Modal.Products;
import com.toyhe.app.Products.Modal.ProductCategorization;
import com.toyhe.app.Products.Repository.ProductCategoryRepository;
import com.toyhe.app.Products.Repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record ProductService(
        ProductRepository productRepository ,
        ProductCategoryRepository  productCategoryRepository ,
        PriceRepository priceRepository
) {
    public ResponseEntity<ProductResponse> createProduct(ProductRequest productRequest) {
        Optional<ProductCategorization> productCategory = productCategoryRepository.findById(productRequest.productCategoryId()) ;
        PriceModel priceModel = priceRepository.findById(productRequest.priceId()).get() ;
        Products products = Products.builder()
                .productName(productRequest.productName())
                .productDescription(productRequest.productDescription())
                .productType(productRequest.productType())
                .productCategorization(productCategory.orElse(null))
                .productPriceModel(priceModel)
                .build();
        productRepository.save(products) ;

        return ResponseEntity.ok(ProductResponse.fromModel(products)) ;
    }
}
