package com.sparta.bobdoduk.store.repository;

import com.sparta.bobdoduk.store.domain.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, UUID> {
}
