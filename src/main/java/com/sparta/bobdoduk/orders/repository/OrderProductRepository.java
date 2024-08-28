package com.sparta.bobdoduk.orders.repository;

import com.sparta.bobdoduk.orders.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {
//    List<OrderProduct> findByOrderId(UUID orderId);
//    List<OrderProduct> findByProductId(UUID productId);
    List<OrderProduct> findByQuantity(int quantity);
    List<OrderProduct> findByPrice(Double price);
}
