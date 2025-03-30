package org.capitalmind.driver.repository.client;

import java.util.Optional;
import java.util.UUID;

import org.capitalmind.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryOrm extends JpaRepository<User, UUID>{

    Optional<User> findByEmail(String email);
}