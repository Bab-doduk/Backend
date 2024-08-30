package com.sparta.bobdoduk.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {
    private String username;
    private String password;
}
