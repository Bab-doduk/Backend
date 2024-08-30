package com.sparta.bobdoduk.auth.service;

import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.auth.dto.UserInfoDto;
import com.sparta.bobdoduk.auth.repository.UserRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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

    @Builder
    @Data
    public static class UserAllResponseDto {
        private final UUID id;
        private final String username;
        private final String role;
    }
}
