package gustavo.ventieri.capitalmind.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gustavo.ventieri.capitalmind.domain.investment.Investment;
import gustavo.ventieri.capitalmind.domain.investment.InvestmentRepository;
import gustavo.ventieri.capitalmind.domain.user.User;

@Repository
public interface JpaInvestmentRepository extends JpaRepository<Investment, Long>, InvestmentRepository {

    @Override
    List<Investment> findAllByUserData(User user);

}