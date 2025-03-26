package org.capitalmind.repository;

import java.util.Optional;

import org.capitalmind.entity.User;

public interface UserRepository {
    User save(User user);

    User update(User user);

    Optional<User> findByEmail(String email);

    void delete(String userId);
} 