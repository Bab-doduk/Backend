package com.sparta.bobdoduk.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionRequestDTO {

    @NotBlank(message = "옵션을 선택해주세요")
    private List<UUID> optionIdList;

}
