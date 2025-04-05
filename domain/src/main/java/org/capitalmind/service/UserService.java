package org.capitalmind.service;

import org.capitalmind.dto.request.UserRequest;
import org.capitalmind.dto.response.UserResponse;
import org.capitalmind.entity.User;

public interface UserService {
    

    void deleteById(String userId);

    void update(String userId, UserRequest updateUserRequest);

    UserResponse getById(String userId);

    User validateAndGetUser(String userId);
}
