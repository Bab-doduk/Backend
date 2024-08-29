package com.sparta.bobdoduk.product.domain;

import com.sparta.bobdoduk.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ai extends BaseEntity {

    @Id
    private UUID id;

    private String userId;

    @Column(length = 2000) // 500자로 길이를 늘림
    private String response;
}
