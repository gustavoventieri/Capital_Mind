package gustavo.ventieri.capitalmind.application.service;

import java.util.List;
import gustavo.ventieri.capitalmind.application.dto.stock.StockRequestDto;
import gustavo.ventieri.capitalmind.application.dto.stock.StockResponseDto;

public interface StockServiceInterface {
    
    void create(StockRequestDto stockRequestDto);

    void update(Long stockId, StockRequestDto stockRequestDto);

    List<StockResponseDto> getAll(String userId);

    StockResponseDto getById(Long stockId);

    void deleteById(Long stockId);

    Double getTotal(Integer quantity, String name);

}
