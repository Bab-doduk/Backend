package com.sparta.bobdoduk.orders.service;

import com.sparta.bobdoduk.orders.domain.OrderProduct;
import com.sparta.bobdoduk.orders.repository.OrderProductRepository;
import com.sparta.bobdoduk.orders.repository.OrderRepository;
import com.sparta.bobdoduk.orders.domain.Order;
import com.sparta.bobdoduk.orders.dto.OrderReqDto;
import com.sparta.bobdoduk.orders.dto.OrderResDto;
import com.sparta.bobdoduk.product.domain.Product;
import com.sparta.bobdoduk.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public OrderResDto createOrder(OrderReqDto orderReqDto) {
        //totalPrice 계산
        Double totalPrice = orderReqDto.getOrderProducts().stream()
                .mapToDouble(orderProductDto -> orderProductDto.getPrice() * orderProductDto.getQuantity())
                .sum();

        Order order = Order.builder()
                .orderId(UUID.randomUUID())
                .userId(orderReqDto.getUserId())
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
                            .orElseThrow(() -> new RuntimeException("Product not found: " + orderProductDto.getProductId()));

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

    public OrderResDto getOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));
        return OrderResDto.fromEntity(order);
    }

    public List<OrderResDto> getAllOrders() {
        return OrderResDto.fromEntityList(orderRepository.findAll());
    }

//    @Transactional
//    public OrderResDto updateOrder(UUID orderId, OrderReqDto orderReqDto) {
//        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));
//
//        //기존 OrderProducts 아예 삭제
//        orderProductRepository.deleteAll(order.getOrderProducts());
//
//        order.setUserId(orderReqDto.getUserId());
//        order.setStoreId(orderReqDto.getStoreId());
//        order.setOrderStatus(orderReqDto.getOrderStatus());
//        order.setOrderType(orderReqDto.getOrderType());
//
//        // 기존 OrderProduct 삭제(orphanremoval=true 로 인해 db상에서도 삭제)
////        order.getOrderProducts().clear();
//
//        // 새로운 OrderProducts 추가
//        List<OrderProduct> updatedOrderProducts = orderReqDto.getOrderProducts().stream()
//                .map(orderProductDto -> {
//                    Product product = productRepository.findById(orderProductDto.getProductId())
//                            .orElseThrow(() -> new RuntimeException("Product not found: " + orderProductDto.getProductId()));
//
//                    // 기존 ID 사용, 없으면 새로운 UUID 생성
//                    UUID orderProductId = orderProductDto.getId() != null
//                            ? orderProductDto.getId()
//                            : UUID.randomUUID();
//
//                    return OrderProduct.builder()
//                            .id(orderProductId)
//                            .order(order)
//                            .product(product)
//                            .quantity(orderProductDto.getQuantity())
//                            .price(orderProductDto.getPrice())
//                            .build();
//                })
//                .collect(Collectors.toList());
//
//        order.setOrderProducts(updatedOrderProducts);
//
//        // 수정된 주문 상품 목록을 바탕으로 totalPrice를 다시 계산
//        Double updatedTotalPrice = updatedOrderProducts.stream()
//                .mapToDouble(orderProduct -> orderProduct.getPrice() * orderProduct.getQuantity())
//                .sum();
//
//        order.setTotalPrice(updatedTotalPrice);
//
//        orderRepository.save(order);
//        orderProductRepository.saveAll(updatedOrderProducts);
//
//        return OrderResDto.fromEntity(order);
//    }

    @Transactional
    public OrderResDto updateOrder(UUID orderId, OrderReqDto orderReqDto) {
        // 기존 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

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
                            .orElseThrow(() -> new RuntimeException("Product not found: " + orderProductDto.getProductId()));

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
    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }

}
