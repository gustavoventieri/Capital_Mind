package org.capitalmind.dto.request;

public record ExpenseRequest(String name, String description, String category, Double price, String userId) {
} 