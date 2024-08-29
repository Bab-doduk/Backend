package com.sparta.bobdoduk.product.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    private UUID id;

    private String name;

    private String description;

    private Double price;

    private ProductStatus productStatus;

    private String image;

    private UUID createUserId;

    private UUID storeId;

}
