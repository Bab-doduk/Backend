package com.sparta.bobdoduk.product.dto;

import com.sparta.bobdoduk.product.domain.Option;
import com.sparta.bobdoduk.product.domain.Product;
import com.sparta.bobdoduk.product.domain.ProductStatus;
import lombok.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionResponseDTO {

    private UUID id;

    private String name;

    private String description;

    private Double price;

    private ProductStatus productStatus;

    private String image;

    private UUID createUserId;

    private UUID storeId;

    // 단일 엔티티를 DTO로 변환하는 메서드
    public static OptionResponseDTO fromEntity(Option option) {
        return OptionResponseDTO.builder()
                .id(option.getId())
                .name(option.getName())
                .description(option.getDescription())
                .price(option.getPrice())
                .productStatus(option.getProductStatus())
                .image(option.getImage())
                .createUserId(option.getCreateUserId())
                .storeId(option.getStoreId())
                .build();
    }

    // 엔티티 리스트를 DTO 리스트로 변환하는 메서드
    public static List<OptionResponseDTO> fromEntityList(List<Option> options) {
        return options.stream()
                .map(OptionResponseDTO::fromEntity) // 각 Product 엔티티를 ProductResponseDTO로 변환
                .collect(Collectors.toList()); // 결과를 리스트로 수집
    }
}
