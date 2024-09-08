package com.sparta.bobdoduk.product.repository;

import com.sparta.bobdoduk.product.domain.Product;
import com.sparta.bobdoduk.product.dto.request.ProductSearchRequestDTO;
import com.sparta.bobdoduk.store.domain.Store;
import com.sparta.bobdoduk.store.dto.request.StoreSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<Product> findProducts(ProductSearchRequestDTO searchDto, Pageable pageable);
}
