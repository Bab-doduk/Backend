package com.sparta.bobdoduk.store.repository;

import com.sparta.bobdoduk.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID>, StoreRepositoryCustom {
}
