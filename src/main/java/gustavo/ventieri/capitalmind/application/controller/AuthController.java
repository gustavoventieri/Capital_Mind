package gustavo.ventieri.capitalmind.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gustavo.ventieri.capitalmind.application.dto.user.auth.LoginRequestDto;
import gustavo.ventieri.capitalmind.application.dto.user.auth.LoginResponseDto;
import gustavo.ventieri.capitalmind.application.dto.user.auth.RegisterRequestDto;
import gustavo.ventieri.capitalmind.application.dto.user.auth.RegisterResponseDto;
import gustavo.ventieri.capitalmind.infrastructure.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    

    private final UserService authService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        // Chama o serviço de autenticação, que retorna um array com o token e o usuário
        String token = authService.login(loginRequestDto);
        
        if (token != null) {
            
            // Crie a resposta utilizando o token e o usuário
            return ResponseEntity.ok(new LoginResponseDto(token));
        }
    
        return new ResponseEntity<>("Invalid Credencials", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDto registerRequestDto){

        String token = authService.register(registerRequestDto);
        
        if(token != null){
            
            return ResponseEntity.ok(new RegisterResponseDto(token));
        }

        
        return new ResponseEntity<>("Sign Up Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    
    }
}
