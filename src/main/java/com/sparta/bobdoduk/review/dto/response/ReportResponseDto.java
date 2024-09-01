package com.sparta.bobdoduk.review.dto.response;

import com.sparta.bobdoduk.review.domain.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportResponseDto {

    private UUID reportId;         // 신고 ID
    private UUID reviewId;         // 리뷰 ID
    private UUID userId;           // 신고한 사용자 ID
    private Boolean isReported;    // 신고 여부
    private String reportMessage;  // 신고 메시지

    public static ReportResponseDto from(Report report, UUID userId) {
        return ReportResponseDto.builder()
                .reportId(report.getReportId())
                .reviewId(report.getReview().getReviewId())
                .userId(userId)
                .isReported(report.getIsReported())
                .reportMessage(report.getReportMessage())
                .build();
    }

}
