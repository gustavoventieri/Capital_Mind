package gustavo.ventieri.capitalmind.domain.user;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.config.security.TokenService;
import gustavo.ventieri.capitalmind.application.core.resources.auth.dto.login.LoginRequestDto;
import gustavo.ventieri.capitalmind.application.core.resources.auth.dto.register.RegisterRequestDto;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    public String login(LoginRequestDto loginRequestDto){
        var user = this.userRepository.findByEmail(loginRequestDto.email()).orElseThrow(() -> new RuntimeException("User not found"));
       
        if(passwordEncoder.matches(loginRequestDto.password(), user.getPassword())){
            String token = this.tokenService.genereateToken(user);
            return token;
        }

        return null;
    }

    public String register(RegisterRequestDto registerRequestDto){
        Optional<User> user = this.userRepository.findByEmail(registerRequestDto.email());
        
        if(user.isEmpty()) {
            var newUser = new User(
                null,
                registerRequestDto.name(),
                registerRequestDto.email(),
                passwordEncoder.encode(registerRequestDto.password()),
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                null,
                Instant.now(),
                Instant.now()
            );
            this.userRepository.save(newUser);
            String token = this.tokenService.genereateToken(newUser);
            return token;
        }

        return null;
    }
}
