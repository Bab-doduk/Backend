package com.sparta.bobdoduk.review.repository;

import com.sparta.bobdoduk.review.domain.Review;
import com.sparta.bobdoduk.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    long countByStoreAndReportIsNull(Store store);

    // 신고된 리뷰를 제외한 리뷰 목록
    @Query("SELECT r FROM Review r LEFT JOIN r.report rp WHERE r.store.storeId = :storeId AND (rp.isReported = false OR rp.isReported IS NULL)")
    List<Review> findByStore_StoreIdAndReport_IsReportedFalse(UUID storeId);
}
