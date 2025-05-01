package org.capitalmind.driver.repository;

import java.util.List;
import java.util.Optional;

import org.capitalmind.driver.repository.client.CryptoCurrencyRepositoryOrm;
import org.capitalmind.entity.CryptoCurrency;
import org.capitalmind.entity.User;
import org.capitalmind.exception.InternalServerError;
import org.capitalmind.exception.NotFound;
import org.capitalmind.repository.CryptoCurrencyRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CryptoCurrencyRepositoryImpl implements CryptoCurrencyRepository {

    private final CryptoCurrencyRepositoryOrm cryptoCurrencyRepositoryOrm;

    @Override
    public CryptoCurrency save(CryptoCurrency cryptoCurrency) {
        try {
            return this.cryptoCurrencyRepositoryOrm.save(cryptoCurrency);
        } catch (Exception exc) {
            throw new InternalServerError(exc);
        }
    }

    @Override
    public CryptoCurrency update(CryptoCurrency cryptoCurrency) {
        try {
            return this.cryptoCurrencyRepositoryOrm.save(cryptoCurrency);
        } catch (Exception exc) {
            throw new InternalServerError(exc);
        }
    }

    @Override
    public Optional<CryptoCurrency> findById(Long cryptoCurrencyId) {
        try {
            return this.cryptoCurrencyRepositoryOrm.findById(cryptoCurrencyId);
        } catch (NotFound exc) {
            throw new NotFound(exc);
        } catch (Exception exc) {
            throw new InternalServerError(exc);
        }
    }

    @Override
    public void delete(Long cryptoCurrencyId) {
        try {
            this.cryptoCurrencyRepositoryOrm.deleteById(cryptoCurrencyId);
        } catch (NotFound exc) {
            throw new NotFound(exc);
        } catch (Exception exc) {
            throw new InternalServerError(exc);
        }
    }

    @Override
    public List<CryptoCurrency> findAllByUserData(User user) {
        try {
            return this.cryptoCurrencyRepositoryOrm.findAllByUserData(user);
        } catch (NotFound exc) {
            throw new NotFound(exc);
        } catch (Exception exc) {
            throw new InternalServerError(exc);
        }
    }
}
