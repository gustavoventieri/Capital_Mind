package org.capitalmind.service;

import org.capitalmind.dto.request.LoginRequest;
import org.capitalmind.dto.request.RegisterRequest;
import org.capitalmind.dto.request.UserRequest;
import org.capitalmind.dto.response.UserResponse;
import org.capitalmind.entity.User;

public interface UserService {
    String login(LoginRequest loginRequest);

    String register(RegisterRequest registerRequest);

    void deleteById(String userId);

    void update(String userId, UserRequest updateUserRequest);

    UserResponse getById(String userId);

    User validateAndGetUser(String userId);
}
