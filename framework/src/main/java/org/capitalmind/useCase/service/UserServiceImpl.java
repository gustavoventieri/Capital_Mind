package org.capitalmind.useCase.service;

import org.capitalmind.driver.repository.UserRepositoryImpl;
import org.capitalmind.dto.request.UserRequest;
import org.capitalmind.dto.response.UserResponse;
import org.capitalmind.entity.User;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateAndGetUser'");
    }
    
}
