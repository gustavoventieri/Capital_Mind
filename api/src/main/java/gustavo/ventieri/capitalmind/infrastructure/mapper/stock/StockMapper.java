package gustavo.ventieri.capitalmind.infrastructure.mapper.stock;

import org.springframework.stereotype.Component;

import gustavo.ventieri.capitalmind.application.dto.stock.StockResponseDto;
import gustavo.ventieri.capitalmind.domain.stock.Stock;

@Component
public class StockMapper {
    
    public StockResponseDto toDto(Stock stock, Double total){
        return new StockResponseDto(
                stock.getStockId(),
                stock.getName(),
                stock.getDescription(),
                stock.getQuantity(),
                total
        );
    }


}
