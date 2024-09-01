package com.sparta.bobdoduk.store.domain;

import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.global.entity.BaseEntity;
import com.sparta.bobdoduk.review.domain.Review;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Table(name = "p_stores")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "store_id")
    private UUID storeId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "food_category_id", nullable = false)
    private FoodCategory foodCategory;

    @ManyToOne
    @JoinColumn(name = "area_category_id", nullable = false)
    private AreaCategory areaCategory;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "average_rating")
    private Double averageRating = 0.0; // 평균 평점

    @Builder
    public Store(String name, FoodCategory foodCategory, AreaCategory areaCategory, String description, String address, String phoneNumber, User owner) {
        this.name = name;
        this.foodCategory = foodCategory;
        this.areaCategory = areaCategory;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.owner = owner;
        this.averageRating = 0.0; // 초기 평균 평점
    }

    public void update(String name, FoodCategory foodCategory, AreaCategory areaCategory, String description, String address, String phoneNumber) {
        this.name = name;
        this.foodCategory = foodCategory;
        this.areaCategory = areaCategory;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // 평균 평점 계산 메서드
    public void calculateAverageRating(List<Review> reviews) {
        if (reviews.isEmpty()) {
            this.averageRating = 0.0;
        } else {
            double totalRating = reviews.stream()
                    .filter(review -> review.getReport() == null || !review.getReport().getIsReported())
                    .mapToDouble(Review::getRating)
                    .sum();
            this.averageRating = totalRating / reviews.size();
        }
    }

    // 새로운 평균 평점 저장
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

}
