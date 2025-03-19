package com.toyhe.app.Products.Repository;

import com.toyhe.app.Products.Modal.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> {
}
