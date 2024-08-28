package com.sparta.bobdoduk.payment.domain;

import jakarta.persistence.*;
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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID paymentId;

    @Column(nullable = false)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Builder
    public Payment(UUID orderId, PaymentMethod paymentMethod, BigDecimal price, PaymentStatus status) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.price = price;
        this.status = status;
    }

}
