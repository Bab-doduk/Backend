package com.sparta.bobdoduk.review.controller;

import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.global.dto.ApiResponseDto;
import com.sparta.bobdoduk.review.dto.request.ReportRequestDto;
import com.sparta.bobdoduk.review.dto.response.ReportListResponseDto;
import com.sparta.bobdoduk.review.dto.response.ReportResponseDto;
import com.sparta.bobdoduk.review.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews/report")
public class ReportController {

    private final ReportService reportService;

    /**
     * 리뷰 신고
     * 리뷰에 대해 신고 생성 (모든 사용자)
     * 신고는 리뷰 작성자와 관리자에게만 보여짐
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<ReportResponseDto>> reportReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                          @RequestBody ReportRequestDto requestDto) {
        UUID userId = userDetails.getUser().getId();
        ReportResponseDto reportDto = reportService.createReport(requestDto.getReviewId(), userId, requestDto.getReportMessage());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDto<>(HttpStatus.CREATED, "리뷰 신고 성공", reportDto));
    }

    /**
     * 특정 리뷰 신고 조회 (관리자)
     */
    @GetMapping("/{reportId}")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<ApiResponseDto<ReportResponseDto>> getReport(@PathVariable UUID reportId) {
        ReportResponseDto reportDto = reportService.getReport(reportId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "신고 조회 성공", reportDto));
    }

    /**
     * 리뷰 신고 삭제 (관리자)
     */
    @DeleteMapping("/{reportId}")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<ApiResponseDto<Void>> deleteReport(@PathVariable UUID reportId) {
        reportService.deleteReport(reportId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "신고 삭제 성공", null));
    }

    /**
     * 모든 리뷰 신고 목록 조회 (관리자)
     */
    @GetMapping
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<ApiResponseDto<Page<ReportListResponseDto>>> getAllReports(Pageable pageable) {
        Page<ReportListResponseDto> reportDtos = reportService.getAllReports(pageable);

        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "전체 신고 목록 조회 성공", reportDtos));
    }



}
