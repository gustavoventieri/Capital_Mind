package org.capitalmind.repository;


import org.capitalmind.entity.User;

public interface UserRepository {
    User save(User user);

    User update(User user);

    void delete(String userId);
} 