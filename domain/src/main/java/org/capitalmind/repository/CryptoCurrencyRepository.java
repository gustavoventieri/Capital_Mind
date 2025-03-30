package org.capitalmind.repository;


import java.util.Optional;

import org.capitalmind.entity.CryptoCurrency;

public interface CryptoCurrencyRepository {
    CryptoCurrency save(CryptoCurrency cryptoCurrency);

    CryptoCurrency update(CryptoCurrency cryptoCurrency);

    Optional<CryptoCurrency> findById(Long cryptoCurrencyId);

    void delete(String cryptoCurrencyId);
}
