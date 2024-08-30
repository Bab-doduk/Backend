package com.sparta.bobdoduk.global.config;

import com.sparta.bobdoduk.auth.security.UserDetailsImpl;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증 정보가 없을 경우 기본 값을 반환
        if (authentication == null) {
            return Optional.empty();
        }

        // 인증된 사용자의 UserDetailsImpl을 가져오고, 사용자 ID를 반환
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Optional.of(userDetails.getUser().getUsername());
    }
}
