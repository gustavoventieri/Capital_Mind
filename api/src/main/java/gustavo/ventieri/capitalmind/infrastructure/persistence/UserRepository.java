package gustavo.ventieri.capitalmind.infrastructure.persistence;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gustavo.ventieri.capitalmind.application.repository.UserRepositoryInterface;
import gustavo.ventieri.capitalmind.domain.user.User;



@Repository
public interface UserRepository extends JpaRepository<User, UUID>, UserRepositoryInterface{

   
}
