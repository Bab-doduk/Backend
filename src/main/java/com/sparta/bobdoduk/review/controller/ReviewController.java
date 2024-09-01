package com.sparta.bobdoduk.review.controller;

import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.global.dto.ApiResponseDto;
import com.sparta.bobdoduk.review.dto.request.ReviewCreateDto;
import com.sparta.bobdoduk.review.dto.response.ReviewResponseDto;
import com.sparta.bobdoduk.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponseDto<ReviewResponseDto>> createReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReviewCreateDto createDto) {

        UUID userId = userDetails.getUser().getId();
        ReviewResponseDto reviewDto = reviewService.createReview(createDto, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDto<>(HttpStatus.CREATED, "리뷰 생성 성공", reviewDto));
    }

}
