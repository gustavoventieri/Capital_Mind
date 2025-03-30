package org.capitalmind.adapter.dto.response;

public record StockResponse(Long stockId, String name, String description, Integer quantity, Double price) {
} 
