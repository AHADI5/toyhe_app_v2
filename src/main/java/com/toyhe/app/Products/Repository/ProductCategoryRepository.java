package com.toyhe.app.Products.Repository;

import com.toyhe.app.Products.Modal.ProductCategorization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategorization, Long> {
}
