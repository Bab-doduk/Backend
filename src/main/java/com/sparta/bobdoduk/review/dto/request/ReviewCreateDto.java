package com.sparta.bobdoduk.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateDto {
    @NotNull
    private UUID storeId;  // 가게 ID

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;  // 평점 (1~5점)

    @NotNull
    private String comment;  // 리뷰 내용
}
