package gustavo.ventieri.capitalmind.application.service;

import java.util.List;

import gustavo.ventieri.capitalmind.application.dto.investment.InvestmentRequestDto;
import gustavo.ventieri.capitalmind.domain.stock.Stock;

public interface StockServiceInterface {
    void create(InvestmentRequestDto investmentRequestDto);

    void update(Long stockId, InvestmentRequestDto investmentRequestDto);

    List<Stock> getAll(String userId);

    Stock getById(Long stockId);

    void deleteById(Long stockId);
}
