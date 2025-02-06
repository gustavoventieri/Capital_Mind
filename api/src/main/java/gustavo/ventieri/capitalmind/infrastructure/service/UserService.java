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

    private final UserRepository userRepository; // Repositório de usuários
    private final PasswordEncoder passwordEncoder; // Encoder de senha para garantir a segurança
    private final TokenService tokenService; // Serviço para gerar tokens JWT
    private final UserMapper userMapper; // Mapeador para converter entre User e UserResponseDto

    /**
     * Valida o ID do usuário e retorna o usuário correspondente.
     */
    @Override
    public User validateAndGetUser(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new InvalidDataException("User ID is Blank or Null");
        }

        UUID userUUID;

        try {
            userUUID = UUID.fromString(userId); // Tenta converter o ID para UUID
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException("Invalid User ID format");
        }

        // Verifica se o usuário existe no banco de dados
        return this.userRepository.findById(userUUID)
            .orElseThrow(() -> new NotFoundException("User Not Found"));
    }

    /**
     * Realiza o login de um usuário, validando as credenciais e gerando um token JWT.
     */
    @Override
    public String login(LoginRequestDto loginRequestDto) {
        // Busca o usuário pelo email
        User user = this.userRepository.findByEmail(loginRequestDto.email())
            .orElseThrow(() -> new NotFoundException("User not found"));

        // Verifica se a senha informada é válida
        if(passwordEncoder.matches(loginRequestDto.password(), user.getPassword())){
            // Gera o token JWT para o usuário
            String token = this.tokenService.genereateToken(user);
            if(token != null) {
                return token;
            }
            throw new InvalidDataException("Invalid Token");
        }

        // Se a senha estiver incorreta, lança uma exceção
        throw new NotFoundException("Invalid Credentials");
    }

    /**
     * Realiza o registro de um novo usuário, validando o email e criando um token JWT.
     */
   @Override
    public String register(RegisterRequestDto registerRequestDto) {
        // Verifica se já existe um usuário com o mesmo email
        Optional<User> user = this.userRepository.findByEmail(registerRequestDto.email());

        if(user.isEmpty()) {
            // Cria um novo usuário
            User newUser = new User(
                null, // ID gerado automaticamente
                registerRequestDto.name(),
                registerRequestDto.email(),
                passwordEncoder.encode(registerRequestDto.password()), // Senha codificada
                registerRequestDto.salary(),
                new ArrayList<>(), // Inicializa as listas vazias
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                Instant.now(),
                Instant.now()
            );

            // Salva o novo usuário no banco de dados
            this.userRepository.save(newUser);

            // Gera o token JWT para o novo usuário
            String token = this.tokenService.genereateToken(newUser);
            if (token != null) {
                return token;
            }
            throw new InvalidDataException("Invalid Token");
        }

        // Se o email já estiver registrado, lança uma exceção
        throw new InvalidDataException("Email Already Registered");
    }

    /**
     * Exclui um usuário pelo seu ID.
     */
    @Override
    public void deleteById(String userId) {
        // Verifica se o usuário existe antes de excluí-lo
        if (!this.userRepository.existsById(UUID.fromString(userId))) {
            throw new NotFoundException("User Not Found");
        }

        // Exclui o usuário do banco de dados
        this.userRepository.deleteById(UUID.fromString(userId));
    }

    /**
     * Atualiza os dados de um usuário existente.
     */
    @Override
    public void update(String userId, UserRequestDto updateUserRequestDto) {
        // Valida e obtém o usuário
        User user = this.validateAndGetUser(userId);
        
        // Atualiza os dados do usuário
        user.setName(updateUserRequestDto.name());
        user.setEmail(updateUserRequestDto.email());
        user.setPassword(passwordEncoder.encode(updateUserRequestDto.password())); // Senha codificada
        user.setSalary(updateUserRequestDto.salary());
        
        // Salva o usuário atualizado no banco de dados
        this.userRepository.save(user);
    }

    /**
     * Obtém os detalhes de um usuário pelo seu ID.
     */
    @Override
    public UserResponseDto getById(String userId) {
        // Valida e obtém o usuário
        User user = this.validateAndGetUser(userId);
        
        // Retorna os dados do usuário mapeados para um DTO
        return userMapper.toDto(user);
    }
}
