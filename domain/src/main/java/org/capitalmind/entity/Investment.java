package org.capitalmind.entity;

import java.time.Instant;

public record Investment(
    Long investmentId,
    String name,
    String description,
    Double price, 
    User userData,
    Instant createAt,
    Instant updateAt
) {
    
}
