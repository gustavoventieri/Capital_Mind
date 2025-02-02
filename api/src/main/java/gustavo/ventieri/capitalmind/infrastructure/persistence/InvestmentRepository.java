package gustavo.ventieri.capitalmind.infrastructure.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gustavo.ventieri.capitalmind.application.repository.InvestmentRepositoryInterface;
import gustavo.ventieri.capitalmind.domain.investment.Investment;




@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long>, InvestmentRepositoryInterface {
    
}