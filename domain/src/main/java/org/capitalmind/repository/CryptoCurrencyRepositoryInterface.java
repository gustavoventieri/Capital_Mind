package org.capitalmind.repository;

import java.util.List;
import java.util.Optional;

import org.capitalmind.entity.CryptoCurrency;
import org.capitalmind.entity.User;

public interface CryptoCurrencyRepositoryInterface {
    CryptoCurrency save(CryptoCurrency cryptoCurrency);

    CryptoCurrency update(CryptoCurrency cryptoCurrency);

    Optional<CryptoCurrency> findById(Long cryptoCurrencyId);

    List<CryptoCurrency> findAllByUserData(User user);

    void delete(String cryptoCurrencyId);
}
