package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.user.UpdateUserRequestDto;
import gustavo.ventieri.capitalmind.application.dto.user.auth.LoginRequestDto;
import gustavo.ventieri.capitalmind.application.dto.user.auth.RegisterRequestDto;
import gustavo.ventieri.capitalmind.application.service.UserServiceInterface;
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.config.security.TokenService;
import gustavo.ventieri.capitalmind.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public String login(LoginRequestDto loginRequestDto) {
        User user = this.userRepository.findByEmail(loginRequestDto.email()).orElseThrow(() -> new RuntimeException("User not found"));

        if(passwordEncoder.matches(loginRequestDto.password(), user.getPassword())){
            String token = this.tokenService.genereateToken(user);
            return token;
        }

        return null;
    }

   @Override
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

    @Override
    public Boolean deleteById(String userId) {

        if (!this.userRepository.existsById(UUID.fromString(userId))) {
            return false;
        }

        this.userRepository.deleteById(UUID.fromString(userId));
        
        return true;

    }

    @Override
    public Boolean update(String userId, UpdateUserRequestDto updateUserRequestDto) {

        if (userId == null || userId.isEmpty()) {
            return false;
        }

        Optional<User> userOptional = this.userRepository.findById(UUID.fromString(userId));
        
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();

        user.setName(updateUserRequestDto.name());
        user.setEmail(updateUserRequestDto.email());
        user.setPassword(updateUserRequestDto.password());
        user.setSalary(updateUserRequestDto.salary());
        

        this.userRepository.save(user);

        return true;
    }

    @Override
    public Optional<User> getById(String userId) {
         if (userId == null) {
            return Optional.empty();
        }

        Optional<User> user = this.userRepository.findById(UUID.fromString(userId));
        
        return user;
    }
    
}