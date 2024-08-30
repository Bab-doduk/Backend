package com.sparta.bobdoduk.auth.dto;


import com.sparta.bobdoduk.auth.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class UserInfoDto {
    private final UUID id;
    private final String username;
    private final String role;
    private final String phone_number;
    private final String address1;
    private final String address2;

    public static UserInfoDto fromEntity(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().toString())
                .phone_number(user.getPhone_number())
                .address1(user.getAddress_1())
                .address2(user.getAddress_2())
                .build();
    }
}
