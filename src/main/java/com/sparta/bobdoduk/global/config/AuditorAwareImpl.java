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
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        // principal이 UserDetailsImpl인지 확인 후 캐스팅
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) principal;
            return Optional.of(userDetails.getUsername());
        }

        // 이외의 경우에는 Optional.empty() 반환
        return Optional.empty();
    }
}
