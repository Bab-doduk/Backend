package com.sparta.bobdoduk.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequestDTO {

    @NotBlank(message = "검색어를 입력해주세요")
    private String query;

    private String sortBy;

    private String orderBy;

    private Integer page;

    private Integer pageSize;
}
