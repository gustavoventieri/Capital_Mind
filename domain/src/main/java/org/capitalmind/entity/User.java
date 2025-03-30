package org.capitalmind.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record User(
    UUID userId,
    String name,
    String email,
    String password,
    Double salary,
    List<Expense> expenses,
    List<Stock> stocks,
    List<Investment> Investments,
    List<CryptoCurrency> cryptoCurrencies,
    Instant createAt,
    Instant updateAt
) {
}
