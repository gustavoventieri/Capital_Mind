package gustavo.ventieri.capitalmind.application.repository;

import java.util.Optional;
import java.util.UUID;

import gustavo.ventieri.capitalmind.domain.user.User;

public interface UserRepositoryInterface {

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);
    
}
