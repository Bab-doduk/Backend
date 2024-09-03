package com.sparta.bobdoduk.product.domain;

import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.global.entity.BaseEntity;
import com.sparta.bobdoduk.store.domain.Store;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p_options")
public class Option extends BaseEntity {

    @Id
    private UUID id;

    private String name;

    private String description;

    private Double price;

    private ProductStatus productStatus;

    private String image;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> productOptions = new ArrayList<>();
}
