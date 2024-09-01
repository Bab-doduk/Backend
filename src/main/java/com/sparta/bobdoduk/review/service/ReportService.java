package com.sparta.bobdoduk.review.service;

import com.sparta.bobdoduk.auth.repository.UserRepository;
import com.sparta.bobdoduk.global.exception.CustomException;
import com.sparta.bobdoduk.global.exception.ErrorCode;
import com.sparta.bobdoduk.review.domain.Report;
import com.sparta.bobdoduk.review.domain.Review;
import com.sparta.bobdoduk.review.dto.response.ReportListResponseDto;
import com.sparta.bobdoduk.review.dto.response.ReportResponseDto;
import com.sparta.bobdoduk.review.repository.ReportRepository;
import com.sparta.bobdoduk.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReviewRepository reviewRepository;

    // 리뷰 신고 생성
    @Transactional
    public ReportResponseDto createReport(UUID reviewId, UUID userId, String reportMessage) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        // 신고 생성 및 저장
        Report report = Report.builder()
                .review(review)
                .isReported(true)
                .reportMessage(reportMessage)
                .build();
        report = reportRepository.save(report);

        return ReportResponseDto.from(report, userId);
    }

    // 특정 리뷰 신고 조회
    @Transactional(readOnly = true)
    public ReportResponseDto getReport(UUID reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPORT_NOT_FOUND));
        return ReportResponseDto.from(report, report.getReview().getUser().getId());
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReport(UUID reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPORT_NOT_FOUND));
        reportRepository.delete(report);
    }

    // 모든 리뷰 신고 목록 조회
    @Transactional(readOnly = true)
    public Page<ReportListResponseDto> getAllReports(Pageable pageable) {
        Page<Report> reports = reportRepository.findAll(pageable);

        Page<ReportListResponseDto> reportDtos = reports.map(report -> {
            Review review = report.getReview();
            return ReportListResponseDto.builder()
                    .reportId(report.getReportId())
                    .reviewId(review.getReviewId())
                    .userId(review.getUser().getId()) // 리뷰 작성자 ID (신고한 사용자 ID)
                    .isReported(report.getIsReported())
                    .reportMessage(report.getReportMessage())
                    .build();
        });

        return reportDtos;
    }
}
