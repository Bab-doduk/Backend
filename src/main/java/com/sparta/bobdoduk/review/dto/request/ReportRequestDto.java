package com.sparta.bobdoduk.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequestDto {
    private UUID reviewId;        // 리뷰 ID
    private String reportMessage; // 신고 메시지
}
