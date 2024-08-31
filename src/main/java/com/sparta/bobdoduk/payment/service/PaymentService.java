package com.sparta.bobdoduk.payment.service;

import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.auth.domain.UserRoleEnum;
import com.sparta.bobdoduk.auth.repository.UserRepository;
import com.sparta.bobdoduk.global.exception.CustomException;
import com.sparta.bobdoduk.global.exception.ErrorCode;
import com.sparta.bobdoduk.payment.domain.Payment;
import com.sparta.bobdoduk.payment.dto.request.PaymentRequestDto;
import com.sparta.bobdoduk.payment.dto.response.PaymentResponseDto;
import com.sparta.bobdoduk.payment.repository.PaymentRepository;
import com.sparta.bobdoduk.store.domain.Store;
import com.sparta.bobdoduk.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.dnd.MouseDragGestureRecognizer;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    // 결제 생성 (유저, 가게주인, 마스터)
    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto request, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .paymentMethod(request.getPaymentMethod())
                .price(request.getPrice())
                .user(user)
                .store(store)
                .build();
        paymentRepository.save(payment);
        return PaymentResponseDto.from(payment);
    }

    // 결제 상세 조회
    @Transactional(readOnly = true)
    public PaymentResponseDto getPayment(UUID paymentId, UUID userId, UserRoleEnum role) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new CustomException(ErrorCode.PAYMENT_NOT_FOUND));

        // MASTER 권한을 가진 사용자는 모든 결제 내역을 조회 가능
        if (role == UserRoleEnum.MASTER) {
            return PaymentResponseDto.from(payment);
        }
        // 일반 사용자는 자신이 생성한 결제만 조회 가능
        if (!payment.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.PAYMENT_ACCESS_DENIED);
        }

        return PaymentResponseDto.from(payment);
    }

    // 결제 목록 전체 조회 (고객)
    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> getAllPaymentsCustomer(UUID userId, UserRoleEnum role, Pageable pageable) {
        // MASTER 권한일 경우, 모든 고객의 결제 목록을 조회
        if (role == UserRoleEnum.MASTER) {
            return paymentRepository.findAll(pageable)
                    .map(PaymentResponseDto::from);
        }

        // 그 외의 경우, 자신의 결제 목록만 조회
        return paymentRepository.findAllByUser_Id(userId, pageable)
                .map(PaymentResponseDto::from);
    }

    // 결제 목록 전체 조회 (가게)
    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> getAllPaymentsStore(UUID ownerId, UserRoleEnum role, Pageable pageable) {
        // MASTER 권한일 경우, 모든 상점의 결제 목록을 조회
        if (role == UserRoleEnum.MASTER) {
            return paymentRepository.findAll(pageable)
                    .map(PaymentResponseDto::from);
        }

        return paymentRepository.findAllByStore_OwnerId(ownerId, pageable)
                .map(PaymentResponseDto::from);
    }

    // 결제 목록 전체 조회 (모든 결제 내역)
    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(PaymentResponseDto::from);
    }
}
