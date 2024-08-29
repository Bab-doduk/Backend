package com.sparta.bobdoduk.orders.dto;

import com.sparta.bobdoduk.orders.domain.OrderProduct;
import com.sparta.bobdoduk.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto {
    private UUID id;
    private UUID productId;
    private int quantity;
    private Double price;

    // OrderProduct -> OrderProductDto 변환
    public static OrderProductDto fromEntity(OrderProduct orderProduct) {
        Product product = orderProduct.getProduct();
        return new OrderProductDto(
                orderProduct.getId(),
                product.getId(),
                orderProduct.getQuantity(),
                orderProduct.getPrice()
        );
    }

    // OrderProduct 리스트 -> OrderProductDto 리스트 변환
    public static List<OrderProductDto> fromEntityList(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(OrderProductDto::fromEntity)
                .collect(Collectors.toList());
    }
}
