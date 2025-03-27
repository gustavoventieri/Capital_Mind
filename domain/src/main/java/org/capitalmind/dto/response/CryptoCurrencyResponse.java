package org.capitalmind.dto.response;

public record CryptoCurrencyResponse(Long cryptoId, String name, String description, Double quantity, Double price) {
}
