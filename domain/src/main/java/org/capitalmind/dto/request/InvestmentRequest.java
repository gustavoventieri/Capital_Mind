package org.capitalmind.dto.request;

public record InvestmentRequest(String name, String description, Double price, String userId) {
} 