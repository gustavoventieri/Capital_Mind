package gustavo.ventieri.capitalmind.application.service;

import java.util.Optional;

import gustavo.ventieri.capitalmind.application.dto.user.UpdateUserRequestDto;
import gustavo.ventieri.capitalmind.application.dto.user.auth.LoginRequestDto;
import gustavo.ventieri.capitalmind.application.dto.user.auth.RegisterRequestDto;
import gustavo.ventieri.capitalmind.domain.user.User;

public interface UserServiceInterface {
    String login(LoginRequestDto loginRequestDto);

    String register(RegisterRequestDto registerRequestDto);

    Boolean deleteById(String userId);

    Boolean update(String userId, UpdateUserRequestDto updateUserRequestDto);

    Optional<User> getById(String userId);

}
