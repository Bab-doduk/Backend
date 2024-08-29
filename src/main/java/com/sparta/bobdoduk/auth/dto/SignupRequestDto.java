package com.sparta.bobdoduk.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;
    @NotBlank
    private String nickname;
    @Email
    private String email;
    @Pattern(regexp = "^[^ ]{8,15}$")
    private String password;
    @NotNull
    private String role;
    @NotNull
    private String phone_number;
    @NotNull
    private String address_1;
    @NotNull
    private String address_2;
}
