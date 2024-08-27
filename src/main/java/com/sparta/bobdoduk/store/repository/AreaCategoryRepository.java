package com.sparta.bobdoduk.store.repository;

import com.sparta.bobdoduk.store.domain.AreaCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AreaCategoryRepository extends JpaRepository<AreaCategory, UUID> {
}
