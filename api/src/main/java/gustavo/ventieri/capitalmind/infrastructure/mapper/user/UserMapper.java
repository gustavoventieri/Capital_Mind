package gustavo.ventieri.capitalmind.infrastructure.mapper.user;

import org.springframework.stereotype.Component;

import gustavo.ventieri.capitalmind.application.dto.user.UserResponseDto;
import gustavo.ventieri.capitalmind.domain.user.User;

@Component
public class UserMapper {
    
      public UserResponseDto toDto(User user){
        return new UserResponseDto(
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            user.getSalary());
    }

}
