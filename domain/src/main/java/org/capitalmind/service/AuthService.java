package org.capitalmind.service;

import org.capitalmind.dto.request.LoginRequest;
import org.capitalmind.dto.request.RegisterRequest;

public interface AuthService {
    String login(LoginRequest loginRequest);

    String register(RegisterRequest registerRequest);
} 
