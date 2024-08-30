package com.sparta.bobdoduk.orders.controller;

import com.sparta.bobdoduk.global.dto.ApiResponseDto;
import com.sparta.bobdoduk.orders.dto.OrderReqDto;
import com.sparta.bobdoduk.orders.dto.OrderResDto;
import com.sparta.bobdoduk.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<OrderResDto>> createOrder(@RequestBody OrderReqDto ordereqDto) {
        OrderResDto orderResDto = orderService.createOrder(ordereqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(HttpStatus.CREATED, "주문 생성 성공", orderResDto));
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<ApiResponseDto<OrderResDto>> getOrder(@PathVariable(name = "order_id") UUID orderId) {
        OrderResDto orderResDto = orderService.getOrder(orderId);
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "주문 조회 성공", orderResDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<OrderResDto>>> getAllOrders(Pageable pageable) {
        Page<OrderResDto> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "주문 목록 전체 조회 성공", orders));
    }

    @PatchMapping("/{order_id}")
    public ResponseEntity<ApiResponseDto<OrderResDto>> updateOrder(@PathVariable(name = "order_id") UUID orderId,
                                                          @RequestBody OrderReqDto orderReqDto) {
        OrderResDto orderResDto = orderService.updateOrder(orderId, orderReqDto);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "주문 수정 성공", orderResDto));
    }

    @DeleteMapping("/{order_id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteOrder(@PathVariable(name = "order_id") UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "주문 삭제 성공", null));
    }
}
