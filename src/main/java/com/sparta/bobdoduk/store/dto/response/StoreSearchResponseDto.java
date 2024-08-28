package com.sparta.bobdoduk.store.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class StoreSearchResponseDto {
    private UUID storeId;
    private String name;
    private String foodCategory;
    private String areaCategory;
    private String description;
    private String address;
    private String phoneNumber;
}
