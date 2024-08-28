package com.sparta.bobdoduk.store.domain;

import com.sparta.bobdoduk.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_area_categories")
@NoArgsConstructor
public class AreaCategory extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "area_category_id")
    private UUID areaCategoryId;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Builder
    public AreaCategory(String name) {
        this.name = name;
    }
}
