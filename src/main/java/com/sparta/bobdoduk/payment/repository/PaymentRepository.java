package com.sparta.bobdoduk.payment.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.payment.domain.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Page<Payment> findAllByUser_Id(UUID userId, Pageable pageable);
}
