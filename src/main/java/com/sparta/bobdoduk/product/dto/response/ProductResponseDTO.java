package com.sparta.bobdoduk.product.dto.response;

import com.sparta.bobdoduk.product.domain.Product;
import com.sparta.bobdoduk.product.domain.ProductStatus;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO implements Serializable {

    private UUID id;

    private String name;

    private String description;

    private Double price;

    private ProductStatus productStatus;

    private String image;

    private UUID createUserId;

    private UUID storeId;

    private List<ProductOptionResponseDTO> productOptions = new ArrayList<>();


    // 단일 엔티티를 DTO로 변환하는 메서드
    public static ProductResponseDTO fromEntity(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .productStatus(product.getProductStatus())
                .image(product.getImage())
                .createUserId(product.getOwner().getId())
                .storeId(product.getStore().getStoreId())
                .productOptions(ProductOptionResponseDTO.fromEntityList(
                        product.getProductOptions() != null ? product.getProductOptions() : new ArrayList<>()
                ))
                .build();
    }

    // 엔티티 리스트를 DTO 리스트로 변환하는 메서드
    public static List<ProductResponseDTO> fromEntityList(List<Product> products) {
        return products.stream()
                .map(ProductResponseDTO::fromEntity) // 각 Product 엔티티를 ProductResponseDTO로 변환
                .collect(Collectors.toList()); // 결과를 리스트로 수집
    }

}
