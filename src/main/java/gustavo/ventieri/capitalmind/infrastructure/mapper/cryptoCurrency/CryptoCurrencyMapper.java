package gustavo.ventieri.capitalmind.infrastructure.mapper.cryptoCurrency;

import org.springframework.stereotype.Component;

import gustavo.ventieri.capitalmind.application.dto.cryptoCurrency.CryptoCurrencyResponseDto;
import gustavo.ventieri.capitalmind.domain.cryptoCurrency.CryptoCurrency;

@Component
public class CryptoCurrencyMapper {
    

     public CryptoCurrencyResponseDto toDto(CryptoCurrency cryptoCurrency, Double total){
        return new CryptoCurrencyResponseDto(
            cryptoCurrency.getCryptoId(),
            cryptoCurrency.getName(),
            cryptoCurrency.getDescription(),
            cryptoCurrency.getQuantity(),
            total
        );
    }
}
