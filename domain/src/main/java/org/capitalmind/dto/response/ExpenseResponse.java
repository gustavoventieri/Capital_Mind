package org.capitalmind.dto.response;

public record ExpenseResponse(Long expenseId, String name, String description, String category, Double price) {
}
