package com.sparta.bobdoduk.auth.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}
