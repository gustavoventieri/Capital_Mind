package org.capitalmind.entity;

import java.time.Instant;

public record Stock(
    Long stockId,
    String name,
    String description,
    Integer quantity,
    User userData,
    Instant createAt,
    Instant updateAt
) { 
}
