package com.sparta.bobdoduk.payment.service;

import com.sparta.bobdoduk.global.exception.CustomException;
import com.sparta.bobdoduk.global.exception.ErrorCode;
import com.sparta.bobdoduk.payment.domain.Payment;
import com.sparta.bobdoduk.payment.dto.request.PaymentRequestDto;
import com.sparta.bobdoduk.payment.dto.response.PaymentResponseDto;
import com.sparta.bobdoduk.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // 결제 생성
    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto request) {
        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .paymentMethod(request.getPaymentMethod())
                .price(request.getPrice())
                .status(request.getStatus())
                .build();
        Payment savedPayment = paymentRepository.save(payment);
        return PaymentResponseDto.from(savedPayment);
    }

    // 결제 상세 조회
    @Transactional(readOnly = true)
    public PaymentResponseDto getPayment(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .map(PaymentResponseDto::from)
                .orElseThrow(() -> new CustomException(ErrorCode.PAYMENT_NOT_FOUND));
    }


}
