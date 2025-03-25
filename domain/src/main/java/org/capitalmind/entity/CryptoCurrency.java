package org.capitalmind.entity;

import java.time.Instant;

public record CryptoCurrency(
    Long cryptoId,
    String name, 
    String description,
    Double quantity,
    User userData,
    Instant createAt,
    Instant updateAt
) {
    
}
