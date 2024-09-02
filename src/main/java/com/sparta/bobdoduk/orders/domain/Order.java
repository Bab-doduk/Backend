package com.sparta.bobdoduk.orders.domain;

import com.sparta.bobdoduk.global.entity.BaseEntity;
import com.sparta.bobdoduk.orders.dto.OrderReqDto;
import com.sparta.bobdoduk.payment.domain.Payment;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="p_orders")
public class Order extends BaseEntity {

    @Id
    private UUID orderId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "store_id")
    private UUID storeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private OrderType orderType;

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts;

//    public Order(OrderReqDto orderReqDto) {
//        this.userId = orderReqDto.getUserId();
//        this.storeId = orderReqDto.getStoreId();
//        this.orderStatus = orderReqDto.getOrderStatus();
//        this.orderType = orderReqDto.getOrderType();
//        this.totalPrice = orderReqDto.getOrderProducts().stream()
//                .mapToDouble(orderProductDto -> orderProductDto.getPrice() * orderProductDto.getQuantity())
//                .sum();
//    }

}
