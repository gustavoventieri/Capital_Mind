package gustavo.ventieri.capitalmind.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import gustavo.ventieri.capitalmind.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);
} 
