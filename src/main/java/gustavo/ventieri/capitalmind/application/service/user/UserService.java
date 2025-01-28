package gustavo.ventieri.capitalmind.application.service.user;

import gustavo.ventieri.capitalmind.application.dto.auth.LoginRequestDto;
import gustavo.ventieri.capitalmind.application.dto.auth.RegisterRequestDto;

public interface UserService {

    String login(LoginRequestDto loginRequestDto);

    String register(RegisterRequestDto registerRequestDto);
}

