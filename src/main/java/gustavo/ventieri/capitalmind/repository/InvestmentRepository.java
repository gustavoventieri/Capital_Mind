package gustavo.ventieri.capitalmind.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gustavo.ventieri.capitalmind.entity.Investment;
import gustavo.ventieri.capitalmind.entity.User;



@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {

  
    List<Investment> findAllByUserData(User user);

}