package gustavo.ventieri.capitalmind.application.repository;

import java.util.Optional;


import gustavo.ventieri.capitalmind.domain.user.User;

public interface UserRepositoryInterface {

    

    Optional<User> findByEmail(String email);
    
}
