package com.sparta.bobdoduk.review.dto.response;

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

}
