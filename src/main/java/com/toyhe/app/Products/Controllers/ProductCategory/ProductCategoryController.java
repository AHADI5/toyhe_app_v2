package com.toyhe.app.Products.Controllers.ProductCategory;

import com.toyhe.app.Products.Dtos.ProductCategoryRequest;
import com.toyhe.app.Products.Dtos.ProductCategoryResponse;
import com.toyhe.app.Products.Services.ProductCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/av1/productCategory")
public record ProductCategoryController(
      ProductCategoryService productCategoryService
) {
    @PostMapping("/")
    ResponseEntity<ProductCategoryResponse> createProductCategory(@RequestBody ProductCategoryRequest productCategory) {
        return productCategoryService.createProductCategory(productCategory) ;
    }
}
