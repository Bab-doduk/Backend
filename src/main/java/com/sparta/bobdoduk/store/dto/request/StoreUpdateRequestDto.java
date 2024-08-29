package com.sparta.bobdoduk.store.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class StoreUpdateRequestDto {

    private String name;                      // 가게 이름
    private UUID foodCategoryId;              // 음식 카테고리 ID
    private UUID areaCategoryId;              // 지역 카테고리 ID
    private String description;               // 가게 설명
    private String address;                   // 가게 주소
    private String phoneNumber;               // 가게 전화번호

}
