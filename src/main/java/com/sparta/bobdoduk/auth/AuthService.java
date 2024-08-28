package com.sparta.bobdoduk.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {

        UserRoleEnum userRole = switch (requestDto.getRole()) {
            case "OWNER" -> UserRoleEnum.OWNER;
            case "MANAGER" -> UserRoleEnum.MANAGER;
            case "MASTER" -> UserRoleEnum.MASTER;
            default -> UserRoleEnum.CUSTOMER;
        };

        String password = passwordEncoder.encode(requestDto.getPassword());

        User user = User.builder()
                .id(UUID.randomUUID())
                .username(requestDto.getUsername())
                .nickname(requestDto.getNickname())
                .email(requestDto.getEmail())
                .password(password)
                .role(userRole)
                .phone_number(requestDto.getPhone_number())
                .address_1(requestDto.getAddress_1())
                .address_2(requestDto.getAddress_2()).build();

        authRepository.save(user);
        return SignupResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .role(user.getRole().toString())
                .build();
    }

}
