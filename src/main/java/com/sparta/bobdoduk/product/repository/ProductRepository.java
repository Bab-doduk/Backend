package com.sparta.bobdoduk.product.repository;

import com.sparta.bobdoduk.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.name LIKE %:query% OR p.description LIKE %:query%")
    Page<Product> searchProducts(@Param("query") String query, Pageable pageable);

}
