package com.sparta.bobdoduk.review.repository;

import com.sparta.bobdoduk.review.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {
    Page<Report> findAll(Pageable pageable);

    // 신고한 사용자 id로 검색
    Page<Report> findByReportedBy_Id(UUID reportedById, Pageable pageable);
}
