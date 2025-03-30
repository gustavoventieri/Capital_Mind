package org.capitalmind.driver.repository;

import java.util.Optional;

import org.capitalmind.driver.repository.client.CryptoCurrencyRepositoryOrm;
import org.capitalmind.entity.CryptoCurrency;
import org.capitalmind.repository.CryptoCurrencyRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Repository
public class CryptoCurrencyRepositoryImpl implements CryptoCurrencyRepository{

    private final CryptoCurrencyRepositoryOrm cryptoCurrencyRepositoryOrm;

    @Override
    public CryptoCurrency save(CryptoCurrency cryptoCurrency) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public CryptoCurrency update(CryptoCurrency cryptoCurrency) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Optional<CryptoCurrency> findById(Long cryptoCurrencyId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public void delete(String cryptoCurrencyId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
