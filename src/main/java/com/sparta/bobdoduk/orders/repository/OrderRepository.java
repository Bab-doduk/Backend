package com.sparta.bobdoduk.orders.repository;

import com.sparta.bobdoduk.orders.domain.Order;
import com.sparta.bobdoduk.orders.domain.OrderStatus;
import com.sparta.bobdoduk.orders.domain.OrderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(UUID userId);
//    List<Order> findByProductId(UUID productId);
    List<Order> findByOrderStatus(OrderStatus orderStatus);
    List<Order> findByOrderType(OrderType orderType);
    List<Order> findByTotalPrice(Double totalPrice);

    Page<Order> findByUserId(UUID id, Pageable pageable);

    // 특정 유저가 특정 가게에서 완료한 주문 찾기
    Optional<Order> findByUserIdAndStoreIdAndOrderStatus(UUID userId, UUID storeId, OrderStatus orderStatus);
}
