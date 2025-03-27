package org.capitalmind.dto.request;

public record StockRequest(String name, String description, Double quantity, String userId) {
}