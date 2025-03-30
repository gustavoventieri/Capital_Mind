package org.capitalmind.adapter.dto.response;

public record ExpenseResponse(Long expenseId, String name, String description, String category, Double price) {
}
