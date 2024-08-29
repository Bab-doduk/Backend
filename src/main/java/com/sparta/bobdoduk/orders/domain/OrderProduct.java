package com.sparta.bobdoduk.orders.domain;

import com.sparta.bobdoduk.orders.dto.OrderProductDto;
import com.sparta.bobdoduk.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="p_order_products")
public class OrderProduct {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    private Double price;

    public OrderProduct(Order order, Product product, int quantity, Double price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

}
