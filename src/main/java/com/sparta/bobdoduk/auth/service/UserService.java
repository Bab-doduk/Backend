package com.sparta.bobdoduk.auth.service;

import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.auth.domain.UserRoleEnum;
import com.sparta.bobdoduk.auth.dto.UpdateUserInfoDto;
import com.sparta.bobdoduk.auth.dto.UserInfoDto;
import com.sparta.bobdoduk.auth.repository.UserRepository;
import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserAllResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserAllResponseDto> AllUsers = new ArrayList<>();
        for (User user : users) {
            AllUsers.add(UserAllResponseDto.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .role(user.getRole().toString())
                            .build());
        }
        return AllUsers;
    }

    public UserInfoDto getUserInfo(User user) {
        return UserInfoDto.fromEntity(user);
    }

    public UserInfoDto updateUserInfo(UserDetailsImpl userDetails, UpdateUserInfoDto userInfo) {
        User user = userDetails.getUser();

        Optional<User> checkUsername = userRepository.findByUsername(userInfo.getUsername());
        if (checkUsername.isPresent() && !userInfo.getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("중복된 아이디가 존재합니다.");
        }

        if (!passwordEncoder.matches(userInfo.getCurrent_password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        UserRoleEnum userRole = switch (userInfo.getRole()) {
            case "OWNER" -> UserRoleEnum.OWNER;
            case "MANAGER" -> UserRoleEnum.MANAGER;
            case "MASTER" -> UserRoleEnum.MASTER;
            default -> UserRoleEnum.CUSTOMER;
        };

        user.setUsername(userInfo.getUsername());
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        user.setRole(userRole);
        user.setPhone_number(userInfo.getPhone_number());
        user.setAddress_1(userInfo.getAddress_1());
        user.setAddress_2(userInfo.getAddress_2());

        userRepository.save(user);

        return UserInfoDto.fromEntity(user);
    }

    public void deleteUserInfo(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        user.setDeletedAt(LocalDateTime.now());
        user.setDeletedBy(userDetails.getUsername());

        userRepository.save(user);
    }

    public List<UserAllResponseDto> searchUser(String query) {
        List<UserAllResponseDto> list = new ArrayList<>();
        List<User> users = switch (query) {
            case "OWNER" -> userRepository.findAllByRole(UserRoleEnum.OWNER);
            case "MANAGER" -> userRepository.findAllByRole(UserRoleEnum.MANAGER);
            case "MASTER" -> userRepository.findAllByRole(UserRoleEnum.MASTER);
            case "CUSTOMER" -> userRepository.findAllByRole(UserRoleEnum.CUSTOMER);
            default -> userRepository.findAllByUsernameContaining(query);
        };

        for (User user : users) {
            list.add(UserAllResponseDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .role(user.getRole().toString())
                    .build());
        }
        return list;
    }

    @Builder
    @Data
    public static class UserAllResponseDto {
        private final UUID id;
        private final String username;
        private final String role;
    }
}
