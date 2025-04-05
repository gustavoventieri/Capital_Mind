package org.capitalmind.driver.config.security;

import java.util.ArrayList;
import java.util.UUID;

import org.capitalmind.driver.repository.UserRepositoryImpl;
import org.capitalmind.entity.User;
import org.capitalmind.exception.NotFound;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepositoryImpl userRepositoryImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UUID userId = UUID.fromString(username);
        User user = this.userRepositoryImpl.findById(userId).orElseThrow(() -> new NotFound("User not found"));;
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
    
}
