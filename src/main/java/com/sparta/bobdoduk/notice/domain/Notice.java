package com.sparta.bobdoduk.notice.domain;

import com.sparta.bobdoduk.orders.domain.OrderProduct;
import com.sparta.bobdoduk.orders.domain.OrderStatus;
import com.sparta.bobdoduk.orders.domain.OrderType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="p_notices")
public class Notice {

    @Id
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

}
