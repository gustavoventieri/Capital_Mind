package org.capitalmind.repository;

import java.util.List;
import java.util.Optional;

import org.capitalmind.entity.Stock;
import org.capitalmind.entity.User;

public interface StockRepository {
    Stock save(Stock stock);

    Stock update(Stock stock);

    Optional<Stock> findById(Long stockId);

    List<Stock> findAllByUserData(User user);

    void delete(String stockId);
}
