package com.sparta.bobdoduk.store.repository;

import com.sparta.bobdoduk.global.exception.CustomException;
import com.sparta.bobdoduk.global.exception.ErrorCode;
import com.sparta.bobdoduk.store.domain.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, UUID> {

    // FoodCategory를 ID로 조회하는 메서드
    default FoodCategory findFoodCategoryById(UUID foodCategoryId) {
        return findById(foodCategoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.FOOD_CATEGORY_NOT_FOUND));
    }
}
