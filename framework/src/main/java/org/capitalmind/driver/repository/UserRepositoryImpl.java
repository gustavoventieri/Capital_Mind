package org.capitalmind.driver.repository;

import java.util.Optional;
import java.util.UUID;

import org.capitalmind.driver.repository.client.UserRepositoryOrm;
import org.capitalmind.entity.User;
import org.capitalmind.exception.InternalServerError;
import org.capitalmind.exception.NotFound;
import org.capitalmind.repository.UserRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository  {

    private final UserRepositoryOrm userRepositoryOrm;

    @Override
    public User save(User user) {
       try {
           User userSaved = userRepositoryOrm.save(user);
           return userSaved;
       } catch (Exception exc) {
            throw new InternalServerError(exc);
       }
    }

    @Override
    public User update(User user) {
       try {
            User userUpdated = userRepositoryOrm.save(user);
            return userUpdated;
       }catch (NotFound exc) {
            throw exc;
       } catch (Exception exc){
            throw new InternalServerError(exc);
       }
    }
    
    @Override
    public void delete(UUID userId) {
       try {
            userRepositoryOrm.deleteById(userId);
       } catch (NotFound exc) {
            throw exc;
       } catch (Exception exc){
            throw new InternalServerError(exc);
       }
    }

    @Override
    public Optional<User> findById(UUID userId) {
        try {
            Optional<User> user = userRepositoryOrm.findById(userId);
            return user;
        } catch (NotFound exc) {
           throw exc;
        }
    }
    
}
