package com.sparta.bobdoduk.payment.service;

import com.sparta.bobdoduk.global.exception.CustomException;
import com.sparta.bobdoduk.global.exception.ErrorCode;
import com.sparta.bobdoduk.payment.domain.Payment;
import com.sparta.bobdoduk.payment.dto.request.PaymentRequestDto;
import com.sparta.bobdoduk.payment.dto.response.PaymentResponseDto;
import com.sparta.bobdoduk.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // 결제 목록 전체 조회 (고객)
    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> getAllPaymentsCustomer(UUID userId, Pageable pageable) {
        // 고객 ID에 대한 필터링 로직 필요
        return paymentRepository.findAllByUser_Id(userId, pageable)
                .map(PaymentResponseDto::from);
    }

    // 결제 목록 전체 조회 (가게)
    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> getAllPaymentsStore(UUID ownerId, Pageable pageable) {
        return paymentRepository.findAllByStore_OwnerId(ownerId, pageable)
                .map(PaymentResponseDto::from);
    }
}
