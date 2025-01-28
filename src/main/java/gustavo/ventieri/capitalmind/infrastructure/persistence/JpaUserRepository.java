package gustavo.ventieri.capitalmind.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.domain.user.UserRepository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, UUID>, UserRepository {

    @Override
    Optional<User> findById(UUID id);

    @Override
    Optional<User> findByEmail(String email);
}
