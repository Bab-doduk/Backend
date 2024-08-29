package com.sparta.bobdoduk.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class SignupResponseDto {
    private final UUID id;
    private final String nickname;
    private final String role;
}
