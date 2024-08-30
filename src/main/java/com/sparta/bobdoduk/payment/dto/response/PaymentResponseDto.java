package com.sparta.bobdoduk.payment.dto.response;

import com.sparta.bobdoduk.payment.domain.Payment;
import com.sparta.bobdoduk.payment.domain.PaymentMethod;
import com.sparta.bobdoduk.payment.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto {
    private UUID paymentId;
    private UUID orderId;
    private PaymentMethod paymentMethod;
    private BigDecimal price;
    private PaymentStatus status;

    public static PaymentResponseDto from(Payment payment) {
        return PaymentResponseDto.builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrderId())
                .paymentMethod(payment.getPaymentMethod())
                .price(payment.getPrice())
                .status(payment.getStatus())
                .build();
    }

}
