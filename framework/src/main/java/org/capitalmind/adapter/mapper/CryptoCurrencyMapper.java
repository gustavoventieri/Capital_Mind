package org.capitalmind.adapter.mapper;

import org.capitalmind.adapter.dto.request.CryptoCurrencyRequestImpl;
import org.capitalmind.dto.request.CryptoCurrencyRequest;
import org.capitalmind.dto.response.CryptoCurrencyResponse;
import org.capitalmind.entity.CryptoCurrency;
import org.springframework.stereotype.Component;

@Component
public class CryptoCurrencyMapper {
     public CryptoCurrencyRequest toDomainCryptoCurrencyRequest(CryptoCurrencyRequestImpl cryptoCurrencyRequestImpl){
        return new CryptoCurrencyRequest(
            cryptoCurrencyRequestImpl.name(), 
            cryptoCurrencyRequestImpl.description(), 
            cryptoCurrencyRequestImpl.quantity(),
            cryptoCurrencyRequestImpl.userId()
        );
    }

    public CryptoCurrencyResponse toCryptoCurrencyResponse(CryptoCurrency cryptoCurrency, Double price){
        return new CryptoCurrencyResponse(
            cryptoCurrency.getCryptoId(), 
            cryptoCurrency.getName(), 
            cryptoCurrency.getDescription(), 
            cryptoCurrency.getQuantity(),
            price

        );
    }
}
