package gustavo.ventieri.capitalmind.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gustavo.ventieri.capitalmind.dto.auth.login.LoginRequestDto;
import gustavo.ventieri.capitalmind.dto.auth.login.LoginResponseDto;
import gustavo.ventieri.capitalmind.dto.auth.register.RegisterRequestDto;
import gustavo.ventieri.capitalmind.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    

    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        // Chama o serviço de autenticação, que retorna um array com o token e o usuário
        Object[] makeResponse = authService.login(loginRequestDto);
        
        if (makeResponse != null) {
            String token = (String) makeResponse[0]; // O token estará na primeira posição
            String user = (String) makeResponse[1];      // O usuário estará na segunda posição
    
            // Crie a resposta utilizando o token e o usuário
            return ResponseEntity.ok(new LoginResponseDto(token, user));
        }
    
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto registerRequestDto){
        Object[] makeResponse = authService.register(registerRequestDto);
        
        if(makeResponse != null){
            String token = (String) makeResponse[0]; // O token estará na primeira posição
            String user = (String) makeResponse[1];      // O usuário estará na segunda posição
    
            // Crie a resposta utilizando o token e o usuário
            return ResponseEntity.ok(new LoginResponseDto(token, user));
        }

        return ResponseEntity.badRequest().build();
    }
}
