package gustavo.ventieri.capitalmind.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gustavo.ventieri.capitalmind.application.repository.CryptoCurrencyRepositoryInterface;
import gustavo.ventieri.capitalmind.domain.cryptoCurrency.CryptoCurrency;

@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long>, CryptoCurrencyRepositoryInterface {
    
}
