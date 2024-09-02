package com.sparta.bobdoduk.review.controller;

import com.sparta.bobdoduk.auth.domain.UserRoleEnum;
import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.global.dto.ApiResponseDto;
import com.sparta.bobdoduk.review.dto.request.ReportRequestDto;
import com.sparta.bobdoduk.review.dto.request.ReviewCreateDto;
import com.sparta.bobdoduk.review.dto.request.ReviewUpdateDto;
import com.sparta.bobdoduk.review.dto.response.ReportResponseDto;
import com.sparta.bobdoduk.review.dto.response.ReviewResponseDto;
import com.sparta.bobdoduk.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 생성 (주문 완료 후 고객)
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto<ReviewResponseDto>> createReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                          @RequestBody ReviewCreateDto createDto) {
        UUID userId = userDetails.getUser().getId();
        ReviewResponseDto reviewDto = reviewService.createReview(createDto, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDto<>(HttpStatus.CREATED, "리뷰 생성 성공", reviewDto));
    }

    /**
     * 상세 리뷰 조회
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponseDto<ReviewResponseDto>> getReview(@PathVariable UUID reviewId) {
        ReviewResponseDto reviewDto = reviewService.getReview(reviewId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "상세 리뷰 조회 성공", reviewDto));
    }

    /**
     * 특정 가게의 전체 리뷰 조회
     */
    @GetMapping("/store/{storeId}")
    public ResponseEntity<ApiResponseDto<List<ReviewResponseDto>>> getReviewsByStore(@PathVariable UUID storeId) {
        List<ReviewResponseDto> reviewDtos = reviewService.getReviewsByStore(storeId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "전체 리뷰 목록 조회 성공", reviewDtos));
    }

    /**
     * 리뷰 수정 (작성자, 관리자)
     */
    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponseDto<Void>> updateReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                             @PathVariable UUID reviewId,
                                                             @RequestBody ReviewUpdateDto updateDto) {
        UUID userId = userDetails.getUser().getId();
        UserRoleEnum role = userDetails.getUser().getRole();
        reviewService.updateReview(reviewId, userId, updateDto, role);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "리뷰 수정 성공", null));
    }

    /**
     * 리뷰 삭제 (작성자, 관리자)
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                             @PathVariable UUID reviewId) {
        UUID userId = userDetails.getUser().getId();
        UserRoleEnum role = userDetails.getUser().getRole();
        reviewService.deleteReview(reviewId, userId, role);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "리뷰 삭제 성공", null));
    }

}
