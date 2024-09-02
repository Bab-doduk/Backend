package com.sparta.bobdoduk.orders.service;

import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.auth.domain.UserRoleEnum;
import com.sparta.bobdoduk.global.exception.CustomException;
import com.sparta.bobdoduk.global.exception.ErrorCode;
import com.sparta.bobdoduk.orders.domain.OrderProduct;
import com.sparta.bobdoduk.orders.repository.OrderProductRepository;
import com.sparta.bobdoduk.orders.repository.OrderRepository;
import com.sparta.bobdoduk.orders.domain.Order;
import com.sparta.bobdoduk.orders.dto.OrderReqDto;
import com.sparta.bobdoduk.orders.dto.OrderResDto;
import com.sparta.bobdoduk.product.domain.Product;
import com.sparta.bobdoduk.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public OrderResDto createOrder(UUID userId, OrderReqDto orderReqDto) {
        //totalPrice 계산
        Double totalPrice = orderReqDto.getOrderProducts().stream()
                .mapToDouble(orderProductDto -> orderProductDto.getPrice() * orderProductDto.getQuantity())
                .sum();

        Order order = Order.builder()
                .orderId(UUID.randomUUID())
                .userId(userId)
                .storeId(orderReqDto.getStoreId())
                .orderStatus(orderReqDto.getOrderStatus())
                .orderType(orderReqDto.getOrderType())
                .totalPrice(totalPrice)
                .build();

        // OrderProduct 객체 생성 및 Order에 추가
        List<OrderProduct> orderProducts = orderReqDto.getOrderProducts().stream()
                .map(orderProductDto -> {
                    // Product 객체 조회
                    Product product = productRepository.findById(orderProductDto.getProductId())
                            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

                    OrderProduct orderProduct = OrderProduct.builder()
                            .id(UUID.randomUUID())
                            .order(order)
                            .product(product)
                            .quantity(orderProductDto.getQuantity())
                            .price(orderProductDto.getPrice())
                            .build();

                    // OrderProduct 객체 생성
                    return orderProduct;
                })
                .collect(Collectors.toList());

        order.setOrderProducts(orderProducts);

        orderRepository.save(order);
        orderProductRepository.saveAll(orderProducts);

        return OrderResDto.fromEntity(order);
    }

    @Transactional(readOnly = true)
    public OrderResDto getOrder(UUID orderId, UUID userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        if(!order.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.USER_MISMATCH);
        }

        return OrderResDto.fromEntity(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderResDto> getAllOrders(User user, Pageable pageable) {
        Page<Order> orders;

        if (user.getRole().equals(UserRoleEnum.MASTER)) {
            // 사용자가 관리자(MASTER)인 경우, 모든 주문을 조회
            orders = orderRepository.findAll(pageable);
        } else {
            // 그 외의 경우, 해당 사용자와 관련된 주문만 조회
            orders = orderRepository.findByUserId(user.getId(), pageable);
        }

        return orders.map(OrderResDto::fromEntity);
    }


    @Transactional
    public OrderResDto updateOrder(UUID orderId, UUID userId, OrderReqDto orderReqDto) {
        // 기존 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        if(!order.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.USER_MISMATCH);
        }

        // totalPrice 계산
        Double totalPrice = orderReqDto.getOrderProducts().stream()
                .mapToDouble(orderProductDto -> orderProductDto.getPrice() * orderProductDto.getQuantity())
                .sum();

        // 주문 정보 업데이트
        order.setOrderStatus(orderReqDto.getOrderStatus());
        order.setOrderType(orderReqDto.getOrderType());
        order.setTotalPrice(totalPrice);

        // 기존 OrderProduct 제거
        orderProductRepository.deleteAll(order.getOrderProducts());
        // 기존 컬렉션 비우기
        order.getOrderProducts().clear();

        // OrderProduct 객체 생성 및 Order에 추가
        List<OrderProduct> orderProducts = orderReqDto.getOrderProducts().stream()
                .map(orderProductDto -> {
                    // Product 객체 조회
                    Product product = productRepository.findById(orderProductDto.getProductId())
                            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

                    OrderProduct orderProduct = OrderProduct.builder()
                            .id(UUID.randomUUID()) // 새로운 UUID 생성
                            .order(order)
                            .product(product)
                            .quantity(orderProductDto.getQuantity())
                            .price(orderProductDto.getPrice())
                            .build();

                    return orderProduct;
                })
                .collect(Collectors.toList());

//        order.setOrderProducts(orderProducts);
        order.getOrderProducts().addAll(orderProducts);

        // 주문 및 OrderProduct 저장
        orderRepository.save(order);
        orderProductRepository.saveAll(orderProducts);

        return OrderResDto.fromEntity(order);
    }


    @Transactional
    public void deleteOrder(UUID orderId, UUID userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        if(!order.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.USER_MISMATCH);
        }

        // 주문 생성 시간이 5분을 초과했는지 체크
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(order.getCreatedAt(), now);

        if (duration.toMinutes() > 5) {
            throw new CustomException(ErrorCode.ORDER_CANNOT_BE_CANCELLED);
        }

        orderRepository.deleteById(orderId);
    }

}
