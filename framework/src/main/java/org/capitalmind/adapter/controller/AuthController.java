package org.capitalmind.adapter.controller;

import org.springframework.http.HttpStatus;
import org.capitalmind.adapter.dto.request.LoginRequestImpl;
import org.capitalmind.adapter.dto.request.RegisterRequestImpl;
import org.capitalmind.adapter.mapper.AuthMapper;
import org.capitalmind.dto.request.LoginRequest;
import org.capitalmind.dto.request.RegisterRequest;
import org.capitalmind.dto.response.AuthResponse;
import org.capitalmind.useCase.service.AuthServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthServiceImpl authServiceImpl;
    private final AuthMapper authMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequestImpl loginRequestImpl) {

        LoginRequest userData = authMapper.toDomainLoginRequest(loginRequestImpl);
        String token = authServiceImpl.login(userData);
        
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(token));
       
    }

     // Realiza o registro de um novo usuário
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequestImpl registerRequestImpl){

        RegisterRequest userData = authMapper.toDomainRegisterRequest(registerRequestImpl);
        String token = authServiceImpl.register(userData);
         
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(token));
    
    }

   



}
