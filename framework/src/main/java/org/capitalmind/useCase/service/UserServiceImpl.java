package org.capitalmind.useCase.service;

import java.util.UUID;

import org.capitalmind.adapter.mapper.UserMapper;
import org.capitalmind.driver.repository.UserRepositoryImpl;
import org.capitalmind.dto.request.UserRequest;
import org.capitalmind.dto.response.UserResponse;
import org.capitalmind.entity.User;
import org.capitalmind.exception.InvalidData;
import org.capitalmind.exception.NotFound;
import org.capitalmind.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepositoryImpl userRepositoryImpl;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    /**
    * Exclui um usuário pelo seu ID.
    */

    @Override
    public void deleteById(String userId) {
         if (this.userRepositoryImpl.findById(UUID.fromString(userId)).isEmpty()) {
            throw new NotFound("User Not Found");
        }

        // Exclui o usuário do banco de dados
        this.userRepositoryImpl.delete(UUID.fromString(userId));
    }


    /**
        * Atualiza os dados de um usuário existente.
    */
    @Override
    public void update(String userId, UserRequest updateUserRequest) {
        User user = this.validateAndGetUser(userId);
        
        // Atualiza os dados do usuário
        user.setName(updateUserRequest.name());
        user.setEmail(updateUserRequest.email());
        user.setPassword(passwordEncoder.encode(updateUserRequest.password())); // Senha codificada
        user.setSalary(updateUserRequest.salary());
        
        // Salva o usuário atualizado no banco de dados
        this.userRepositoryImpl.save(user);
    }


    /**
     * Obtém os detalhes de um usuário pelo seu ID.
     */
    @Override
    public UserResponse getById(String userId) {
        User user = this.validateAndGetUser(userId);

        return userMapper.toUserResponse(user);

    }

    /**
     * Valida o ID do usuário e retorna o usuário correspondente.
     */
    @Override
    public User validateAndGetUser(String userId) {
         if (userId == null || userId.isEmpty()) {
            throw new InvalidData("User ID is Blank or Null");
        }

        UUID userUUID;

        try {
            userUUID = UUID.fromString(userId); // Tenta converter o ID para UUID
        } catch (IllegalArgumentException e) {
            throw new InvalidData("Invalid User ID format");
        }

        // Verifica se o usuário existe no banco de dados
        return this.userRepositoryImpl.findById(userUUID)
            .orElseThrow(() -> new NotFound("User Not Found"));
    }
    
}
