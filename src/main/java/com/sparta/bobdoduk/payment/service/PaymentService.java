package com.sparta.bobdoduk.payment.service;

import com.sparta.bobdoduk.payment.domain.Payment;
import com.sparta.bobdoduk.payment.dto.request.PaymentRequestDto;
import com.sparta.bobdoduk.payment.dto.response.PaymentResponseDto;
import com.sparta.bobdoduk.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // 결제 생성
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

}
