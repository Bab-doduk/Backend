package com.sparta.bobdoduk.payment.dto.request;

import com.sparta.bobdoduk.payment.domain.PaymentMethod;
import com.sparta.bobdoduk.payment.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSearchDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private PaymentMethod paymentMethod;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private PaymentStatus status;
    private UUID storeId;
}
