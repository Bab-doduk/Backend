package com.sparta.bobdoduk.payment.domain;

import com.sparta.bobdoduk.auth.domain.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Builder
    public Payment(UUID orderId, PaymentMethod paymentMethod, BigDecimal price, PaymentStatus status, User user, Store store) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.price = price;
        this.status = status;
        this.user = user;
        this.store = store;
    }

    public UUID getUserId() {
        return this.user.getId();
    }

}
