package org.capitalmind.repository;



import java.util.UUID;

import org.capitalmind.entity.User;

public interface UserRepository {
    User save(User user);

    User update(User user);

    void delete(UUID userId);

    User findById(UUID userId);
} 