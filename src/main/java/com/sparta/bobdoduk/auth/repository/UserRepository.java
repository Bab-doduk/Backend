package com.sparta.bobdoduk.auth.repository;

import com.sparta.bobdoduk.auth.domain.User;
import com.sparta.bobdoduk.auth.domain.UserRoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    Page<User> findAllByUsernameContaining(String username, Pageable pageable);
    Page<User> findAllByRole(UserRoleEnum role, Pageable pageable);
}
