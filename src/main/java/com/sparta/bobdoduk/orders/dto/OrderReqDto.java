package com.sparta.bobdoduk.orders.dto;

import com.sparta.bobdoduk.orders.domain.OrderStatus;
import com.sparta.bobdoduk.orders.domain.OrderType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderReqDto {

    private UUID userId;
    private UUID storeId;
    private OrderStatus orderStatus; // 주문 상태 (예: 완료)
    private OrderType orderType;   // 주문 유형 (ONLINE, OFFLINE)
    private List<OrderProductDto> orderProducts; // 주문 상품 목록

}
