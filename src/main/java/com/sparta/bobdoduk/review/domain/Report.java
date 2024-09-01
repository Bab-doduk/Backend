package com.sparta.bobdoduk.review.domain;

import com.sparta.bobdoduk.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_reports")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "report_id")
    private UUID reportId;

    @OneToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(name = "is_reported", nullable = false)
    private Boolean isReported; // 신고 여부

    @Column(name = "report_message", columnDefinition = "text")
    private String reportMessage; // 신고 메시지

    @Builder
    public Report(Review review, Boolean isReported, String reportMessage) {
        this.review = review;
        this.isReported = isReported;
        this.reportMessage = reportMessage;
    }

}
