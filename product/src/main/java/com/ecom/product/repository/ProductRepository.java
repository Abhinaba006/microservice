package com.ecom.product.repository;

import com.ecom.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Collection<Product> findByActiveTrue();

    @Query("select p from products p where LOWER(p.productName) like LOWER(CONCAT('%',:name,'%' )) and p.active=true and p.stockQuantity>0")
    Collection<Product> searchProduct(String name);
}