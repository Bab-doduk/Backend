package com.sparta.bobdoduk.product.dto;

import com.sparta.bobdoduk.product.domain.Option;
import com.sparta.bobdoduk.product.domain.Product;
import com.sparta.bobdoduk.product.domain.ProductOption;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionResponseDTO {

    private UUID id;

    private OptionResponseDTO option;

    // 단일 엔티티를 DTO로 변환하는 메서드
    public static ProductOptionResponseDTO fromEntity(ProductOption productOption) {
        return ProductOptionResponseDTO.builder()
                .id(productOption.getId())
                .option(OptionResponseDTO.fromEntity(productOption.getOption()))
                .build();
    }

    // 엔티티 리스트를 DTO 리스트로 변환하는 메서드
    public static List<ProductOptionResponseDTO> fromEntityList(List<ProductOption> productOptions) {
        return productOptions.stream()
                .map(ProductOptionResponseDTO::fromEntity) // 각 Product 엔티티를 ProductResponseDTO로 변환
                .collect(Collectors.toList()); // 결과를 리스트로 수집
    }

}
