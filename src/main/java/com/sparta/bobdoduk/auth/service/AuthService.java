package com.sparta.bobdoduk.auth.service;

import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.auth.domain.UserRoleEnum;
import com.sparta.bobdoduk.auth.dto.SignupRequestDto;
import com.sparta.bobdoduk.auth.dto.SignupResponseDto;
import com.sparta.bobdoduk.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {

        String username = requestDto.getUsername();
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

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
                .password(password)
                .role(userRole)
                .phone_number(requestDto.getPhone_number())
                .address_1(requestDto.getAddress_1())
                .address_2(requestDto.getAddress_2()).build();

        userRepository.save(user);
        return SignupResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().toString())
                .build();
    }

}
