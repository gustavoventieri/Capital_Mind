package gustavo.ventieri.capitalmind.application.dto.cryptoCurrency;

public record CryptoCurrencyResponseDto(Long cryptoId, String name, String description, Double quantity, Double price) {
    
}
