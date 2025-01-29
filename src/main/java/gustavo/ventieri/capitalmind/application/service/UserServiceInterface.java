package gustavo.ventieri.capitalmind.application.service;

import gustavo.ventieri.capitalmind.application.dto.auth.LoginRequestDto;
import gustavo.ventieri.capitalmind.application.dto.auth.RegisterRequestDto;

public interface UserServiceInterface {
    String login(LoginRequestDto loginRequestDto);

    String register(RegisterRequestDto registerRequestDto);
}
