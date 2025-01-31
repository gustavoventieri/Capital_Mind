package gustavo.ventieri.capitalmind.application.service;

import java.util.List;

import gustavo.ventieri.capitalmind.application.dto.cryptoCurrency.CryptoCurrencyRequestDto;
import gustavo.ventieri.capitalmind.domain.cryptoCurrency.CryptoCurrency;

public interface CryptoCurrencyServiceInterface {

    void create(CryptoCurrencyRequestDto cryptoCurrencyRequestDto);

    void update(Long cryptoId, CryptoCurrencyRequestDto cryptoCurrencyRequestDto);

    List<CryptoCurrency> getAll(String userId);

    CryptoCurrency getById(Long cryptoId);

    void deleteById(Long cryptoId);
    
} 