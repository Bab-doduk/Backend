package com.sparta.bobdoduk.review.service;

import com.sparta.bobdoduk.auth.repository.UserRepository;
import com.sparta.bobdoduk.global.exception.CustomException;
import com.sparta.bobdoduk.global.exception.ErrorCode;
import com.sparta.bobdoduk.review.domain.Report;
import com.sparta.bobdoduk.review.domain.Review;
import com.sparta.bobdoduk.review.dto.response.ReportResponseDto;
import com.sparta.bobdoduk.review.repository.ReportRepository;
import com.sparta.bobdoduk.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
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
}
