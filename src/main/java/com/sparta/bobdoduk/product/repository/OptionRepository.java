package com.sparta.bobdoduk.product.repository;

import com.sparta.bobdoduk.product.domain.Option;
import com.sparta.bobdoduk.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OptionRepository extends JpaRepository<Option, UUID> {

    @Query("SELECT o " +
            "FROM Option o " +
            "WHERE o.name LIKE %:query% OR o.description LIKE %:query%")
    Page<Option> searchOptions(@Param("query") String query, Pageable pageable);
}
