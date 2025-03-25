package org.capitalmind.repository;

import java.util.List;
import java.util.Optional;

import org.capitalmind.entity.User;

public interface UserRepositoryInterface {

    User save(User user);

    User update(User user);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void delete(String userId);
    
} 