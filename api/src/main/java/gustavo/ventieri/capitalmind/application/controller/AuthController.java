package gustavo.ventieri.capitalmind.application.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gustavo.ventieri.capitalmind.application.dto.user.auth.LoginRequestDto;
import gustavo.ventieri.capitalmind.application.dto.user.auth.RegisterRequestDto;
import gustavo.ventieri.capitalmind.application.dto.user.auth.AuthResponseDto;
import gustavo.ventieri.capitalmind.infrastructure.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    

    private final UserService authService;
    

    // Realiza a autenticação de um usuário existente
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {

        String token = authService.login(loginRequestDto);
        
        return ResponseEntity.ok(new AuthResponseDto(token));
       
    }


    // Realiza o registro de um novo usuário
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody @Valid RegisterRequestDto registerRequestDto){

        String token = authService.register(registerRequestDto);
         
        return ResponseEntity.ok(new AuthResponseDto(token));
    
    }
}
