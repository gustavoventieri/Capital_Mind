package org.capitalmind.adapter.mapper;

import org.capitalmind.adapter.dto.request.StockRequestImpl;
import org.capitalmind.dto.request.StockRequest;
import org.capitalmind.dto.response.StockResponse;
import org.capitalmind.entity.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {
      
    public StockRequest toDomainStockRequest(StockRequestImpl stockRequestImpl){
        return new StockRequest(
           stockRequestImpl.name(),
           stockRequestImpl.description(),
           stockRequestImpl.quantity(),
           stockRequestImpl.userId()
        );
    }

    public StockResponse toStockResponse(Stock stock, Double total){
        return new StockResponse(
            stock.getStockId(),
            stock.getName(),
            stock.getDescription(),
            stock.getQuantity(),
            total
        );
    }
}
