package com.sparta.bobdoduk.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        log.info(signupRequestDto.getAddress_1());
        User user = User.builder()
                .id(UUID.randomUUID())
                .username(signupRequestDto.getUsername())
                .nickname(signupRequestDto.getNickname())
                .email(signupRequestDto.getEmail())
                .password(signupRequestDto.getPassword())
                .role(signupRequestDto.getRole())
                .phone_number(signupRequestDto.getPhone_number())
                .address_1(signupRequestDto.getAddress_1())
                .address_2(signupRequestDto.getAddress_2()).build();

        authRepository.save(user);
        return SignupResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();
    }
}
