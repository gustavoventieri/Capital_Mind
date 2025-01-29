package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.auth.LoginRequestDto;
import gustavo.ventieri.capitalmind.application.dto.auth.RegisterRequestDto;
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.config.security.TokenService;
import gustavo.ventieri.capitalmind.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    public String login(LoginRequestDto loginRequestDto) {
        User user = this.userRepository.findByEmail(loginRequestDto.email()).orElseThrow(() -> new RuntimeException("User not found"));

        if(passwordEncoder.matches(loginRequestDto.password(), user.getPassword())){
            String token = this.tokenService.genereateToken(user);
            return token;
        }

        return null;
    }

   
    public String register(RegisterRequestDto registerRequestDto) {
        Optional<User> user = this.userRepository.findByEmail(registerRequestDto.email());

        if(user.isEmpty()) {
            User newUser = new User(
                null,
                registerRequestDto.name(),
                registerRequestDto.email(),
                passwordEncoder.encode(registerRequestDto.password()),
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
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
