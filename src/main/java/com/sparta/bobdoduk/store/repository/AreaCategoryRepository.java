package com.sparta.bobdoduk.store.repository;

import com.sparta.bobdoduk.global.exception.CustomException;
import com.sparta.bobdoduk.global.exception.ErrorCode;
import com.sparta.bobdoduk.store.domain.AreaCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AreaCategoryRepository extends JpaRepository<AreaCategory, UUID> {

    // AreaCategory를 ID로 조회하는 메서드
    default AreaCategory findAreaCategoryById(UUID areaCategoryId) {
        return findById(areaCategoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.AREA_CATEGORY_NOT_FOUND));
    }
}
