package com.sparta.bobdoduk.orders.controller;

import com.sparta.bobdoduk.orders.dto.OrderReqDto;
import com.sparta.bobdoduk.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderReqDto ordereqDto) {
        return ResponseEntity.ok().body(orderService.createOrder(ordereqDto));
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<?> getOrder(@PathVariable(name = "order_id") UUID orderId) {
        return ResponseEntity.ok().body(orderService.getOrder(orderId));
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    @PatchMapping("/{order_id}")
    public ResponseEntity<?> updateOrder(@PathVariable(name = "order_id") UUID orderId,
                                                   @RequestBody OrderReqDto orderReqDto) {
        return ResponseEntity.ok().body(orderService.updateOrder(orderId, orderReqDto));
    }

    @DeleteMapping("/{order_id}")
    public ResponseEntity<?> deleteOrder(@PathVariable(name = "order_id") UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
