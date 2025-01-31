package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.user.UserRequestDto;
import gustavo.ventieri.capitalmind.application.dto.user.UserResponseDto;
import gustavo.ventieri.capitalmind.application.dto.user.auth.LoginRequestDto;
import gustavo.ventieri.capitalmind.application.dto.user.auth.RegisterRequestDto;
import gustavo.ventieri.capitalmind.application.service.UserServiceInterface;
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.config.security.TokenService;
import gustavo.ventieri.capitalmind.infrastructure.exception.InvalidDataException;  
import gustavo.ventieri.capitalmind.infrastructure.exception.NotFoundException;
import gustavo.ventieri.capitalmind.infrastructure.mapper.user.UserMapper;
import gustavo.ventieri.capitalmind.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    @Override
    public User validateAndGetUser(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new InvalidDataException("User ID is Blank or Null");
        }

        UUID userUUID;

        try {
            userUUID = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException("Invalid User ID format");
        }

        return this.userRepository.findById(userUUID)
            .orElseThrow(() -> new NotFoundException("User Not Found"));
    }


    @Override
    public String login(LoginRequestDto loginRequestDto) {
        User user = this.userRepository.findByEmail(loginRequestDto.email()).orElseThrow(() -> new NotFoundException("User not found"));

        if(passwordEncoder.matches(loginRequestDto.password(), user.getPassword())){
            String token = this.tokenService.genereateToken(user);
            if(token != null) {
                return token;
            }
            throw new InvalidDataException("Invalid Token");
        }

        throw new NotFoundException("Invalid Credentials");
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
            if (token != null) {
                return token;
            }
            throw new InvalidDataException("Invalid Token");
        }

        throw new InvalidDataException("Invalid Data Provided");
    }

    @Override
    public void deleteById(String userId) {

        if (!this.userRepository.existsById(UUID.fromString(userId))) throw new NotFoundException("User Not Found");

        this.userRepository.deleteById(UUID.fromString(userId));
        
    }

    @Override
    public void update(String userId, UserRequestDto updateUserRequestDto) {

        User user = this.validateAndGetUser(userId);
        
        user.setName(updateUserRequestDto.name());
        user.setEmail(updateUserRequestDto.email());
        user.setPassword(passwordEncoder.encode(updateUserRequestDto.password()));
        user.setSalary(updateUserRequestDto.salary());
        
        this.userRepository.save(user);
    }

    @Override
    public UserResponseDto getById(String userId) {
       
        User user = this.validateAndGetUser(userId);
        
        return userMapper.toDto(user);
    }
    
}