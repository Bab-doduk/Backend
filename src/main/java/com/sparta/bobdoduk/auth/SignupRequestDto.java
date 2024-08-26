package com.sparta.bobdoduk.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class SignupRequestDto {
    private String username;
    private String nickname;
    private String email;
    private String password;
    private String role;
    private String phone_number;
    private String address_1;
    private String address_2;
}
