package com.sparta.bobdoduk.review.dto.response;

import com.sparta.bobdoduk.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {

    private UUID reviewId;  // 리뷰 ID
    private UUID storeId;  // 가게 ID
    private UUID userId;  // 사용자 ID
    private Integer rating;  // 평점
    private String comment;  // 리뷰 내용

    public static ReviewResponseDto from(Review review) {
        return ReviewResponseDto.builder()
                .reviewId(review.getReviewId())
                .storeId(review.getStore().getStoreId())
                .userId(review.getUser().getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .build();
    }

}
