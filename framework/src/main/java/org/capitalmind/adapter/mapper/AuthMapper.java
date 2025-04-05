package org.capitalmind.adapter.mapper;

import org.capitalmind.adapter.dto.request.LoginRequestImpl;
import org.capitalmind.adapter.dto.request.RegisterRequestImpl;
import org.capitalmind.dto.request.LoginRequest;
import org.capitalmind.dto.request.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    
    public LoginRequest toDomainLoginRequest(LoginRequestImpl loginRequestImpl){
        return new LoginRequest(loginRequestImpl.email(), loginRequestImpl.password());
    }

     public RegisterRequest toDomainRegisterRequest(RegisterRequestImpl registerRequestImpl){
        return new RegisterRequest(registerRequestImpl.name(),registerRequestImpl.email(), registerRequestImpl.salary(), registerRequestImpl.password());
    }
}
