package com.sparta.bobdoduk.payment.controller;

import com.sparta.bobdoduk.auth.domain.UserRoleEnum;
import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.global.dto.ApiResponseDto;
import com.sparta.bobdoduk.payment.dto.request.PaymentRequestDto;
import com.sparta.bobdoduk.payment.dto.response.PaymentResponseDto;
import com.sparta.bobdoduk.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 결제 생성
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<PaymentResponseDto>> createPayment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                            @RequestBody PaymentRequestDto request) {
        UUID userId = userDetails.getUser().getId();
        PaymentResponseDto createdPayment = paymentService.createPayment(request, userId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.CREATED, "결제 생성 성공", createdPayment));
    }

    /**
     * 결제 상세 조회
     */
    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponseDto<PaymentResponseDto>> getPayment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                         @PathVariable UUID paymentId) {
        UUID userId = userDetails.getUser().getId();
        UserRoleEnum role = userDetails.getUser().getRole();
        PaymentResponseDto payment = paymentService.getPayment(paymentId, userId, role);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "결제 상세정보 조회 성공", payment));
    }

    /**
     * 결제 목록 전체 조회 (고객)
     */
    @GetMapping("/customer")
    public ResponseEntity<ApiResponseDto<Page<PaymentResponseDto>>> getAllPaymentsCustomer(@AuthenticationPrincipal UserDetailsImpl userDetails, Pageable pageable) {
        UUID userId = userDetails.getUser().getId();
        UserRoleEnum role = userDetails.getUser().getRole();
        Page<PaymentResponseDto> payments = paymentService.getAllPaymentsCustomer(userId, role, pageable);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "고객 결제 목록 조회 성공", payments));
    }

    /**
     * 결제 목록 전체 조회 (가게)
     */
    @GetMapping("/store")
    public ResponseEntity<ApiResponseDto<Page<PaymentResponseDto>>> getAllPaymentsForStore(@AuthenticationPrincipal UserDetailsImpl userDetails, Pageable pageable) {
        UUID ownerId = userDetails.getUser().getId();
        UserRoleEnum role = userDetails.getUser().getRole();
        Page<PaymentResponseDto> payments = paymentService.getAllPaymentsStore(ownerId, role, pageable);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "상점 결제 목록 조회 성공", payments));
    }

    /**
     * 결제 목록 전체 조회 (모든 결제 내역)
     */
    @GetMapping
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<ApiResponseDto<Page<PaymentResponseDto>>> getAllPayments(Pageable pageable) {
        Page<PaymentResponseDto> payments = paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "모든 결제 목록 조회 성공", payments));
    }



}
