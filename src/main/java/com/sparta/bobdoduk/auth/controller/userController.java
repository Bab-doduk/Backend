package com.sparta.bobdoduk.auth.controller;

import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.auth.dto.UpdateUserInfoDto;
import com.sparta.bobdoduk.auth.dto.UserInfoDto;
import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import com.sparta.bobdoduk.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('OWNER')")
    public ResponseEntity<UserInfoDto> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(userService.getUserInfo(user));
    }

    @PutMapping("/user")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('OWNER')")
    public ResponseEntity<UserInfoDto> updateUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @RequestBody UpdateUserInfoDto userInfo) {
        return ResponseEntity.ok(userService.updateUserInfo(userDetails,userInfo));
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUserInfo(userDetails);
        return ResponseEntity.ok("Delete user " + userDetails.getUsername());
    }
}
