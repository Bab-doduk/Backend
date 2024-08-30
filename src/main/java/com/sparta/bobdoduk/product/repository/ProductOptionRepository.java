package com.sparta.bobdoduk.product.repository;

import com.sparta.bobdoduk.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, UUID> {
    void deleteByProduct_IdAndOption_Id(UUID productId, UUID optionId);
}
