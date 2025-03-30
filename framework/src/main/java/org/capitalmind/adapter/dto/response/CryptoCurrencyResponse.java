package org.capitalmind.adapter.dto.response;

public record CryptoCurrencyResponse(Long cryptoId, String name, String description, Double quantity, Double price) {
}
