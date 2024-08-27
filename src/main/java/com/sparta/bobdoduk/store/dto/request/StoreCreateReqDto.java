package com.sparta.bobdoduk.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreCreateReqDto {

    @NotBlank(message = "가게 이름은 필수입니다.")
    private String name;

    @NotNull(message = "음식 카테고리 ID는 필수입니다.")
    private UUID foodCategoryId;

    @NotNull(message = "지역 카테고리 ID는 필수입니다.")
    private UUID areaCategoryId;

    @NotBlank(message = "가게 설명은 필수입니다.")
    private String description;

    @NotBlank(message = "가게 주소는 필수입니다.")
    private String address;

    @NotBlank(message = "가게 전화번호는 필수입니다.")
    private String phoneNumber;

    @NotNull(message = "가게 주인 ID는 필수입니다.")
    private UUID ownerId;
}
