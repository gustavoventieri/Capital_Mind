package gustavo.ventieri.capitalmind.application.core.resources.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gustavo.ventieri.capitalmind.application.core.resources.auth.dto.login.LoginRequestDto;
import gustavo.ventieri.capitalmind.application.core.resources.auth.dto.login.LoginResponseDto;
import gustavo.ventieri.capitalmind.application.core.resources.auth.dto.register.RegisterRequestDto;
import gustavo.ventieri.capitalmind.application.core.resources.auth.dto.register.RegisterResponseDto;
import gustavo.ventieri.capitalmind.domain.user.UserService;
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
    
        return ResponseEntity.badRequest().body("Invalid Credencials");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDto registerRequestDto){
       String token = authService.register(registerRequestDto);
        
        if(token != null){
            
            return ResponseEntity.ok(new RegisterResponseDto(token));
        }

        return ResponseEntity.badRequest().body("Something Wrong");
        
        }
}
