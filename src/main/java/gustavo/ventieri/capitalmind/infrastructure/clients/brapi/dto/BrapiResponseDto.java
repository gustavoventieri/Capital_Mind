package gustavo.ventieri.capitalmind.infrastructure.clients.brapi.dto;

import java.util.List;

public record BrapiResponseDto(List<StockDto> results) {
    
}
