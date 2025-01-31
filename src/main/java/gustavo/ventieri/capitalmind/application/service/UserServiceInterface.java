package gustavo.ventieri.capitalmind.application.service;

import gustavo.ventieri.capitalmind.application.dto.user.UpdateUserRequestDto;
import gustavo.ventieri.capitalmind.application.dto.user.auth.LoginRequestDto;
import gustavo.ventieri.capitalmind.application.dto.user.auth.RegisterRequestDto;
import gustavo.ventieri.capitalmind.domain.user.User;
public interface UserServiceInterface {
    
    String login(LoginRequestDto loginRequestDto);

    String register(RegisterRequestDto registerRequestDto);

    void deleteById(String userId);

    void update(String userId, UpdateUserRequestDto updateUserRequestDto);

    User getById(String userId);

}
