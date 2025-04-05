package org.capitalmind.driver.config.security;


import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import org.capitalmind.driver.repository.UserRepositoryImpl;
import org.capitalmind.entity.User;
import org.capitalmind.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepositoryImpl userRepositoryImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);

        if (login != null) {
            UUID userId = UUID.fromString(login);
            User user = userRepositoryImpl.findById(userId).orElseThrow(() -> new NotFound("User not found"));;

            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

            var authetication = new UsernamePasswordAuthenticationToken(user, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authetication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
    
}
