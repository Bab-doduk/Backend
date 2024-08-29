package com.sparta.bobdoduk.orders.dto;

import com.sparta.bobdoduk.orders.domain.OrderStatus;
import com.sparta.bobdoduk.orders.domain.OrderType;
import com.sparta.bobdoduk.orders.domain.Order;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResDto {

    private UUID orderId;
    private UUID userId;
    private UUID storeId;
    private OrderStatus orderStatus;
    private OrderType orderType;
    private Double totalPrice;
    private List<OrderProductDto> orderProducts;

    public static OrderResDto fromEntity(Order order) {
        return OrderResDto.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .storeId(order.getStoreId())
                .orderStatus(order.getOrderStatus())
                .orderType(order.getOrderType())
                .totalPrice(order.getTotalPrice())
                .orderProducts(OrderProductDto.fromEntityList(order.getOrderProducts()))
                .build();
    }

    public static List<OrderResDto> fromEntityList(List<Order> orders) {
        return orders.stream()
                .map(OrderResDto::fromEntity)
                .collect(Collectors.toList());
    }
}
