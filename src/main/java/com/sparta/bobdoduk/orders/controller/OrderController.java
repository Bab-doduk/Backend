package com.sparta.bobdoduk.orders.controller;

import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<OrderResDto>> createOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody OrderReqDto ordereqDto) {

        UUID userId = userDetails.getUser().getId();
        OrderResDto orderResDto = orderService.createOrder(userId, ordereqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(HttpStatus.CREATED, "주문 생성 성공", orderResDto));
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<ApiResponseDto<OrderResDto>> getOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable(name = "order_id") UUID orderId) {
        UUID userId = userDetails.getUser().getId();
        OrderResDto orderResDto = orderService.getOrder(orderId, userId);
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "주문 조회 성공", orderResDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<OrderResDto>>> getAllOrders(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Pageable pageable) {
        User user = userDetails.getUser();
        Page<OrderResDto> orders = orderService.getAllOrders(user, pageable);
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "주문 목록 전체 조회 성공", orders));
    }

    @PatchMapping("/{order_id}")
    public ResponseEntity<ApiResponseDto<OrderResDto>> updateOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable(name = "order_id") UUID orderId,
            @RequestBody OrderReqDto orderReqDto) {
        UUID userId = userDetails.getUser().getId();
        OrderResDto orderResDto = orderService.updateOrder(orderId, userId, orderReqDto);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "주문 수정 성공", orderResDto));
    }

    @DeleteMapping("/{order_id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable(name = "order_id") UUID orderId) {
        UUID userId = userDetails.getUser().getId();
        orderService.deleteOrder(orderId, userId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "주문 삭제 성공", null));
    }
}
