package org.capitalmind.repository;


import java.util.Optional;
import java.util.UUID;

import org.capitalmind.entity.User;

public interface UserRepository {
    User save(User user);

    User update(User user);

    void delete(UUID userId);

    Optional<User> findById(UUID userId);
} 