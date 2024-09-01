package com.sparta.bobdoduk.review.domain;

import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.global.entity.BaseEntity;
import com.sparta.bobdoduk.store.domain.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private UUID reviewId;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "rating", nullable = false)
    private Integer rating; // 1-5 점수

    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    @OneToOne(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private Report report; // 리뷰 신고 관련

    @Builder
    public Review(Store store, User user, Integer rating, String comment) {
        this.store = store;
        this.user = user;
        this.rating = rating;
        this.comment = comment;
    }

    public void updateReview(Integer rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }
}
