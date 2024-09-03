package com.sparta.bobdoduk.product.dto.request;

import com.sparta.bobdoduk.product.domain.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "상품 이름을 입력해주세요")
    private String name;

    @NotBlank(message = "상품 설명을 입력해주세요")
    private String description;

    @NotBlank(message = "상품 가격을 입력해주세요")
    private Double price;

    @NotBlank(message = "상품 상태를 입력해주세요")
    private ProductStatus productStatus;

    @NotBlank(message = "상품 이미지를 입력해주세요")
    private String image;

    private UUID storeId;
}
