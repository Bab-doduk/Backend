package com.sparta.bobdoduk.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportListResponseDto {

    private UUID reportId;         // 신고 ID
    private UUID reviewId;         // 리뷰 ID
    private UUID userId;           // 리뷰를 쓴 사용자 ID
    private Boolean isReported;    // 신고 여부
    private String reportMessage;  // 신고 메시지

}
