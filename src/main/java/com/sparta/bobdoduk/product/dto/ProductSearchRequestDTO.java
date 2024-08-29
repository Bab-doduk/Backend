package com.sparta.bobdoduk.product.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequestDTO {

    private String query;

    private String sortBy;

    private String order;

    private Integer page;

    private Integer pageSize;
}
