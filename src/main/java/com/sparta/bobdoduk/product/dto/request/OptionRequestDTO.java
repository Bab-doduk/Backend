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
public class OptionRequestDTO {

    @NotBlank(message = "옵션 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "옵션 설명은 필수입니다.")
    private String description;

    @NotBlank(message = "옵션 가격은 필수입니다.")
    private Double price;

    @NotBlank(message = "옵션 상태는 필수입니다.")
    private ProductStatus productStatus;

    @NotBlank(message = "옵션 이미지는 필수입니다.")
    private String image;

    private UUID storeId;
}
