package gustavo.ventieri.capitalmind.application.repository;

import java.util.Optional;
import java.util.UUID;

import gustavo.ventieri.capitalmind.domain.user.User;

public interface UserRepository {

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    User save(User user);

    void deleteById(UUID id);
}
