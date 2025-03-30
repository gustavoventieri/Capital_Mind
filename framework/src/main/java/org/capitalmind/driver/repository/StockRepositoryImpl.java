package org.capitalmind.driver.repository;

import java.util.Optional;

import org.capitalmind.driver.repository.client.StockRepositoryOrm;
import org.capitalmind.entity.Stock;
import org.capitalmind.repository.StockRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Repository
public class StockRepositoryImpl implements StockRepository {

    private final StockRepositoryOrm stockRepositoryOrm;

    @Override
    public Stock save(Stock stock) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Stock update(Stock stock) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Optional<Stock> findById(Long stockId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public void delete(String stockId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
