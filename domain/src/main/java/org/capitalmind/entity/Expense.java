package org.capitalmind.entity;

import java.time.Instant;

public record Expense(
    Long expenseId,
    String name, 
    String description,
    String category,
    Double price,
    User userData,
    Instant createAt,
    Instant updateAt
) {
    
}
