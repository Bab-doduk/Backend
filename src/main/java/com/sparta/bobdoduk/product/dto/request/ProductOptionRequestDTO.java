package com.sparta.bobdoduk.product.dto.request;

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
