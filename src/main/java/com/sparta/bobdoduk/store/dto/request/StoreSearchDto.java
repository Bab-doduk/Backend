package com.sparta.bobdoduk.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StoreSearchDto {

    private String name;
    private UUID foodCategoryId;
    private UUID areaCategoryId;
    private String description;
    private String address;

}