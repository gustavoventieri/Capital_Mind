package org.capitalmind.dto.request;

public record StockRequest(String name, String description, Integer quantity, String userId) {
}