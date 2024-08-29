package com.sparta.bobdoduk.auth.security;

import com.sparta.bobdoduk.auth.repository.AuthRepository;
import com.sparta.bobdoduk.auth.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = authRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + email));

        return new UserDetailsImpl(user);
    }
}
