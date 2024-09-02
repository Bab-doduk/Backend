package com.sparta.bobdoduk.auth.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserInfoDto {
    private String current_password;

    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;
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
