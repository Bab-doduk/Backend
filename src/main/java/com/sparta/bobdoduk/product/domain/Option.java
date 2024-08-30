package com.sparta.bobdoduk.product.domain;

import com.sparta.bobdoduk.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p_options")
public class Option extends BaseEntity {

    @Id
    private UUID id;

    private String name;

    private String description;

    private Double price;

    private ProductStatus productStatus;

    private String image;

    private UUID createUserId;

    private UUID storeId;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> productOptions;
}
