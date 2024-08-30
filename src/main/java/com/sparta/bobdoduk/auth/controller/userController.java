package com.sparta.bobdoduk.auth.controller;

import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.auth.dto.UserResponseDto;
import com.sparta.bobdoduk.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class userController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('MASTER') or hasRole('MANAGER')")
    public ResponseEntity<List<UserService.UserAllResponseDto>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }
}
