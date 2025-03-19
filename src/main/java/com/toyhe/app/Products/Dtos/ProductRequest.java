package com.toyhe.app.Products.Dtos;

public record ProductRequest(
       String productName,
       String productDescription,
       String productType ,
       long productCategoryId  ,
       long priceId
) {
}
