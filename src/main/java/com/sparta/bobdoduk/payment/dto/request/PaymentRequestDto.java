package com.sparta.bobdoduk.payment.dto.request;

import com.sparta.bobdoduk.payment.domain.PaymentMethod;
import com.sparta.bobdoduk.payment.domain.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {
    private UUID orderId;
    private PaymentMethod paymentMethod;
    private BigDecimal price;
    private UUID storeId;
}
