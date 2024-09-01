package com.sparta.bobdoduk.review.repository;

import com.sparta.bobdoduk.review.domain.Review;
import com.sparta.bobdoduk.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    long countByStoreAndReportIsNull(Store store);
}
