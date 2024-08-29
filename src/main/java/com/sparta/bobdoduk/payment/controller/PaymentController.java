package com.sparta.bobdoduk.payment.controller;

import com.sparta.bobdoduk.global.dto.ApiResponseDto;
import com.sparta.bobdoduk.payment.dto.request.PaymentRequestDto;
import com.sparta.bobdoduk.payment.dto.response.PaymentResponseDto;
import com.sparta.bobdoduk.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 결제 생성
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<PaymentResponseDto>> createPayment(@RequestBody PaymentRequestDto request) {
        PaymentResponseDto createdPayment = paymentService.createPayment(request);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.CREATED, "결제 생성 성공", createdPayment));
    }
}
