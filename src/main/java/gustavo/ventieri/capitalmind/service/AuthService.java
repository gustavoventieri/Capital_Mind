package gustavo.ventieri.capitalmind.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.dto.auth.login.LoginRequestDto;
import gustavo.ventieri.capitalmind.dto.auth.register.RegisterRequestDto;
import gustavo.ventieri.capitalmind.entity.User;
import gustavo.ventieri.capitalmind.infra.security.TokenService;
import gustavo.ventieri.capitalmind.repository.UserRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    public Object[] login(LoginRequestDto loginRequestDto){
        var user = this.userRepository.findByEmail(loginRequestDto.email()).orElseThrow(() -> new RuntimeException("User not found"));
       
        if(passwordEncoder.matches(loginRequestDto.password(), user.getPassword())){
            String token = this.tokenService.genereateToken(user);

            Object[] makeResponse = {user.getName(), token};

            return makeResponse;
        }

        return null;
    }

    public Object[] register(RegisterRequestDto registerRequestDto){
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
                Instant.now(),
                Instant.now()
            );
            this.userRepository.save(newUser);
            String token = this.tokenService.genereateToken(newUser);
            
            Object[] makeResponse = {newUser.getName(), token};

            return makeResponse;
        }

        return null;
    }
}
