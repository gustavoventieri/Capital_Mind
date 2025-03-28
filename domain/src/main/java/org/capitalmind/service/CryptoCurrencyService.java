package org.capitalmind.service;

import java.util.List;

import org.capitalmind.dto.request.CryptoCurrencyRequest;
import org.capitalmind.dto.response.CryptoCurrencyResponse;

public interface CryptoCurrencyService {
    void create(CryptoCurrencyRequest cryptoCurrencyRequest);

    void update(Long cryptoCurrencyId, CryptoCurrencyRequest cryptoCurrencyRequest);

    List<CryptoCurrencyResponse> getAll(String userId);

    CryptoCurrencyResponse getById(Long cryptoCurrencyId);

    void deleteById(Long cryptoCurrencyId);

    Double getPrice(String ids, String currency, Double quantity);
}
