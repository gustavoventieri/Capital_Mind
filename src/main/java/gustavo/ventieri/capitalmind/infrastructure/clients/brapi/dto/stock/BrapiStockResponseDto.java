package gustavo.ventieri.capitalmind.infrastructure.clients.brapi.dto.stock;

import java.util.List;

public record BrapiStockResponseDto(List<StockDto> results) {
    
}
