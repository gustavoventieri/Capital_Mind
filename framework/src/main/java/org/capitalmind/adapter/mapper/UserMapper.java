package org.capitalmind.adapter.mapper;

import org.capitalmind.adapter.dto.request.UserRequestImpl;
import org.capitalmind.dto.request.UserRequest;
import org.capitalmind.dto.response.UserResponse;
import org.capitalmind.entity.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
     public UserRequest toDomainUserRequest(UserRequestImpl userRequestImpl){
        return new UserRequest(
           userRequestImpl.name(),
           userRequestImpl.email(),
           userRequestImpl.salary(),
           userRequestImpl.password()
        );
    }
    public UserResponse toUserResponse(User user){
        return new UserResponse(
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            user.getSalary()
        );
    }
}
