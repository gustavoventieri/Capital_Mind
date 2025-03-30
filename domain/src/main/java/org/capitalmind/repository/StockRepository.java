package org.capitalmind.repository;


import java.util.Optional;
import org.capitalmind.entity.Stock;

public interface StockRepository {
    Stock save(Stock stock);

    Stock update(Stock stock);

    Optional<Stock> findById(Long stockId);

    void delete(String stockId);
}
