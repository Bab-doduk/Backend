package com.sparta.bobdoduk.store.repository;

import com.sparta.bobdoduk.store.domain.Store;
import com.sparta.bobdoduk.store.dto.request.StoreSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepositoryCustom {
    Page<Store> findStores(StoreSearchDto searchDto, Pageable pageable);

}
