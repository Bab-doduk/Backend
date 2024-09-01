package com.sparta.bobdoduk.auth.domain;

import com.sparta.bobdoduk.Auditing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Where(clause = "deleted_at is null")
@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "p_users")
public class User extends Auditing {
    @Id
    private UUID id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;
    @Column(nullable = false)
    private String phone_number;
    @Column(nullable = false)
    private String address_1;
    @Column(nullable = false)
    private String address_2;
}
