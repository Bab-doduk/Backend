package com.sparta.bobdoduk.store.domain;

import com.sparta.bobdoduk.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_food_categories")
@NoArgsConstructor
public class FoodCategory extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "food_category_id")
    private UUID foodCategoryId;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Builder
    public FoodCategory(String name) {
        this.name = name;
    }
}
