package com.sparta.bobdoduk.payment.repository;

import com.sparta.bobdoduk.payment.domain.Payment;
import com.sparta.bobdoduk.payment.dto.request.PaymentSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentRepositoryCustom {
    Page<Payment> searchPayments(PaymentSearchDto searchDto, Pageable pageable);
}
