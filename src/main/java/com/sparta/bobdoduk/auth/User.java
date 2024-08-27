package com.sparta.bobdoduk.auth;

import com.sparta.bobdoduk.Auditing;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "p_users")
public class User extends Auditing {
    @Id
    private UUID id;
    private String username;
    private String nickname;
    private String email;
    private String password;
    private String role;
    private String phone_number;
    private String address_1;
    private String address_2;
}
