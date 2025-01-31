package gustavo.ventieri.capitalmind.application.service;

import java.util.List;

import gustavo.ventieri.capitalmind.application.dto.cryptoCurrency.CryptoCurrencyRequestDto;
import gustavo.ventieri.capitalmind.application.dto.cryptoCurrency.CryptoCurrencyResponseDto;

public interface CryptoCurrencyServiceInterface {

    void create(CryptoCurrencyRequestDto cryptoCurrencyRequestDto);

    void update(Long cryptoCurrencyId, CryptoCurrencyRequestDto cryptoCurrencyRequestDto);

    List<CryptoCurrencyResponseDto> getAll(String userId);

    CryptoCurrencyResponseDto getById(Long cryptoCurrencyId);

    void deleteById(Long cryptoCurrencyId);
    
} 