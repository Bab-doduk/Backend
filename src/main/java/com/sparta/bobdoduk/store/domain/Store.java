package com.sparta.bobdoduk.store.domain;

import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.global.entity.BaseEntity;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Table(name = "p_stores")
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

    @Builder
    public Store(String name, FoodCategory foodCategory, AreaCategory areaCategory, String description, String address, String phoneNumber, User owner) {
        this.name = name;
        this.foodCategory = foodCategory;
        this.areaCategory = areaCategory;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.owner = owner;
    }

    public void update(String name, FoodCategory foodCategory, AreaCategory areaCategory, String description, String address, String phoneNumber) {
        this.name = name;
        this.foodCategory = foodCategory;
        this.areaCategory = areaCategory;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

}
