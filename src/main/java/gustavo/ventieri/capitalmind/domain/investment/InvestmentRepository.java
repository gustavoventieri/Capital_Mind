package gustavo.ventieri.capitalmind.domain.investment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gustavo.ventieri.capitalmind.domain.user.User;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    List<Investment> findAllByUserData(User userData);
}