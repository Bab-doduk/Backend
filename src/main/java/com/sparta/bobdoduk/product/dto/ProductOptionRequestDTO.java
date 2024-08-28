package com.sparta.bobdoduk.product.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionRequestDTO {

    private List<UUID> optionIdList;

}
