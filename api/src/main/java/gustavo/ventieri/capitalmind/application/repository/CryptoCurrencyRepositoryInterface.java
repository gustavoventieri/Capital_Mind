package gustavo.ventieri.capitalmind.application.repository;

import java.util.List;

import gustavo.ventieri.capitalmind.domain.cryptoCurrency.CryptoCurrency;
import gustavo.ventieri.capitalmind.domain.user.User;

public interface CryptoCurrencyRepositoryInterface {

    List<CryptoCurrency> findAllByUserData(User user);
    
}
