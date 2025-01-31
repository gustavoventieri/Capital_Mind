package gustavo.ventieri.capitalmind.application.service;

import java.util.List;

import gustavo.ventieri.capitalmind.application.dto.stock.StockRequestDto;
import gustavo.ventieri.capitalmind.domain.stock.Stock;

public interface StockServiceInterface {
    void create(StockRequestDto stockRequestDto);

    void update(Long stockId, StockRequestDto stockRequestDto);

    List<Stock> getAll(String userId);

    Stock getById(Long stockId);

    void deleteById(Long stockId);
}
