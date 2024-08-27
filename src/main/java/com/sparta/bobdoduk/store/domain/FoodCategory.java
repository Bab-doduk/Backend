package com.sparta.bobdoduk.store.domain;

import com.sparta.bobdoduk.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_food_categories")
public class FoodCategory extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "food_category_id")
    private UUID foodCategoryId;

    @Column(name = "name", length = 50, nullable = false)
    private String name;
}
