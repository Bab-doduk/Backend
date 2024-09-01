package com.sparta.bobdoduk.payment.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.bobdoduk.payment.domain.Payment;
import com.sparta.bobdoduk.payment.domain.PaymentMethod;
import com.sparta.bobdoduk.payment.domain.PaymentStatus;
import com.sparta.bobdoduk.payment.domain.QPayment;
import com.sparta.bobdoduk.payment.dto.request.PaymentSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.sparta.bobdoduk.auth.domain.QUser.user;
import static com.sparta.bobdoduk.payment.domain.QPayment.payment;
import static com.sparta.bobdoduk.store.domain.QStore.store;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Payment> searchPayments(PaymentSearchDto searchDto, Pageable pageable) {
        QPayment payment = QPayment.payment;
        BooleanBuilder builder = new BooleanBuilder();

        if (searchDto.getStartDate() != null) {
            builder.and(payment.createdAt.goe(searchDto.getStartDate()));
        }
        if (searchDto.getEndDate() != null) {
            builder.and(payment.createdAt.loe(searchDto.getEndDate()));
        }
        if (searchDto.getPaymentMethod() != null) {
            builder.and(payment.paymentMethod.eq(searchDto.getPaymentMethod()));
        }
        if (searchDto.getMinPrice() != null) {
            builder.and(payment.price.goe(searchDto.getMinPrice()));
        }
        if (searchDto.getMaxPrice() != null) {
            builder.and(payment.price.loe(searchDto.getMaxPrice()));
        }
        if (searchDto.getStatus() != null) {
            builder.and(payment.status.eq(searchDto.getStatus()));
        }
        if (searchDto.getStoreId() != null) {
            builder.and(payment.store.storeId.eq(searchDto.getStoreId()));
        }

        JPAQuery<Payment> query = queryFactory
                .selectFrom(payment)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        long total = query.fetchCount();

        return new PageImpl<>(query.fetch(), pageable, total);
    }

}
