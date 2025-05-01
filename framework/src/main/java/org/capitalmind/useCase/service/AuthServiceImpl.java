package org.capitalmind.useCase.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import org.capitalmind.driver.config.security.TokenService;
import org.capitalmind.driver.repository.UserRepositoryImpl;
import org.capitalmind.dto.request.LoginRequest;
import org.capitalmind.dto.request.RegisterRequest;
import org.capitalmind.entity.User;
import org.capitalmind.exception.BadRequest;
import org.capitalmind.exception.InternalServerError;
import org.capitalmind.exception.InvalidData;
import org.capitalmind.exception.NotFound;
import org.capitalmind.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepositoryImpl userRepositoryImpl;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    /**
     * Realiza o login de um usuário, validando as credenciais e gerando um token JWT.
     */

    @Override
    public String login(LoginRequest loginRequest) {
        // Verifica se o usuário existe no banco de dados
        User user = this.userRepositoryImpl.findByEmail(loginRequest.email())
                .orElseThrow(() -> new NotFound("User not found"));

        // Verifica se a senha informada é válida
        if (passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            // Gera o token JWT para o usuário
            String token = this.tokenService.generateToken(user);
            if (token != null) {
                return token;
            }
            // Lança uma exceção caso o token não seja gerado corretamente
            throw new InternalServerError("Invalid Token");
        }

        // Se a senha estiver incorreta, lança uma exceção
        throw new InvalidData("Invalid Credentials");
    }

    /**
    * Realiza o registro de um novo usuário, validando o email e criando um token JWT.
    */
    @Override
    public String register(RegisterRequest registerRequest) {
        // Verifica se o usuário já está registrado com o email fornecido
        Optional<User> user = this.userRepositoryImpl.findByEmail(registerRequest.email());

        if (user.isEmpty()) {
            // Cria um novo usuário
            User newUser = new User(
                null, // ID gerado automaticamente
                registerRequest.name(),
                registerRequest.email(),
                passwordEncoder.encode(registerRequest.password()), // Senha codificada
                registerRequest.salary(),
                new ArrayList<>(), // Inicializa as listas vazias
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                Instant.now(),
                Instant.now()
            );

            // Salva o novo usuário no banco de dados
            this.userRepositoryImpl.save(newUser);

            // Gera o token JWT para o novo usuário
            String token = this.tokenService.generateToken(newUser);

            if (token != null) {
                return token;
            }

            // Lança uma exceção caso o token não seja gerado corretamente
            throw new InternalServerError("Invalid Token");
        }

        // Se o email já estiver registrado, lança uma exceção
        throw new BadRequest("Email already registered");
    }

    
}
