package com.sparta.bobdoduk.review.domain;

import com.sparta.bobdoduk.auth.domain.User;
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

    @ManyToOne
    @JoinColumn(name = "reported_by", nullable = false)
    private User reportedBy; // 신고한 유저

    @ManyToOne
    @JoinColumn(name = "reported_user", nullable = false)
    private User reportedUser; // 신고 당한 유저 (리뷰 작성자)

    @Column(name = "is_reported", nullable = false)
    private Boolean isReported; // 신고 여부

    @Column(name = "report_message", columnDefinition = "text")
    private String reportMessage; // 신고 메시지

    @Builder
    public Report(Review review, User reportedBy, User reportedUser, Boolean isReported, String reportMessage) {
        this.review = review;
        this.reportedBy = reportedBy;
        this.reportedUser = reportedUser;
        this.isReported = isReported;
        this.reportMessage = reportMessage;
    }

}
