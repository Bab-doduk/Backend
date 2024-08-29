package com.sparta.bobdoduk.store.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class StoreListResponseDto {
    private UUID storeId;
    private String name;
    private String foodCategoryName;
    private String areaCategoryName;
}
