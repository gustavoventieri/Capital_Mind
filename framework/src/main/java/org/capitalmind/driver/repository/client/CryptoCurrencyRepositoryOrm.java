package org.capitalmind.driver.repository.client;

import java.util.List;

import org.capitalmind.entity.CryptoCurrency;
import org.capitalmind.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoCurrencyRepositoryOrm extends JpaRepository<CryptoCurrency, Long>{
    List<CryptoCurrency> findAllByUserData(User user);
}
