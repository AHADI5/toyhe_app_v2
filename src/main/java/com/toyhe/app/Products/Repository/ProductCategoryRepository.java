package com.toyhe.app.Products.Repository;

import com.toyhe.app.Products.Modal.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
