package com.sparta.bobdoduk.payment.domain;

import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.global.entity.BaseEntity;
import com.sparta.bobdoduk.orders.domain.Order;
import com.sparta.bobdoduk.store.domain.Store;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import java.util.UUID;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "p_payments")
@Getter
@NoArgsConstructor
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING; // 기본값 설정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Builder
    public Payment(Order order, PaymentMethod paymentMethod, BigDecimal price, PaymentStatus status, User user, Store store) {
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.price = price;
        this.status = status != null ? status : PaymentStatus.PENDING;
        this.user = user;
        this.store = store;
    }

    public UUID getUserId() {
        return this.user.getId();
    }

    public UUID getOrderId() { return this.order.getOrderId();}
}
