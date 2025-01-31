package gustavo.ventieri.capitalmind.application.dto.stock;

public record StockResponseDto(Long stockId, String name, String description, Integer quantity, Double price) {
    
}
