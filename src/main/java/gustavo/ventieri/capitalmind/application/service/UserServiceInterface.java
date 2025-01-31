package gustavo.ventieri.capitalmind.application.service;

import gustavo.ventieri.capitalmind.application.dto.user.UserRequestDto;
import gustavo.ventieri.capitalmind.application.dto.user.auth.LoginRequestDto;
import gustavo.ventieri.capitalmind.application.dto.user.auth.RegisterRequestDto;
import gustavo.ventieri.capitalmind.domain.user.User;
public interface UserServiceInterface {
    
    String login(LoginRequestDto loginRequestDto);

    String register(RegisterRequestDto registerRequestDto);

    void deleteById(String userId);

    void update(String userId, UserRequestDto updateUserRequestDto);

    User getById(String userId);

    User validateAndGetUser(String userId);

}
