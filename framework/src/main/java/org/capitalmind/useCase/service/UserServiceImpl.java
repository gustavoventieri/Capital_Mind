package org.capitalmind.useCase.service;

import java.util.UUID;

import org.capitalmind.driver.repository.UserRepositoryImpl;
import org.capitalmind.dto.request.UserRequest;
import org.capitalmind.dto.response.UserResponse;
import org.capitalmind.entity.User;
import org.capitalmind.exception.InvalidData;
import org.capitalmind.exception.NotFound;
import org.capitalmind.service.UserService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepositoryImpl userRepositoryImpl;

    @Override
    public void deleteById(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public void update(String userId, UserRequest updateUserRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public UserResponse getById(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

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
