package com.sparta.bobdoduk.product.dto.request;

import com.sparta.bobdoduk.product.domain.ProductStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionRequestDTO {

    private String name;

    private String description;

    private Double price;

    private ProductStatus productStatus;

    private String image;

    private UUID createUserId;

    private UUID storeId;
}
