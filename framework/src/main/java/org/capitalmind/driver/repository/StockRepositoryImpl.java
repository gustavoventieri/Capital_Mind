package org.capitalmind.driver.repository;

import java.util.List;
import java.util.Optional;

import org.capitalmind.driver.repository.client.StockRepositoryOrm;
import org.capitalmind.entity.Stock;
import org.capitalmind.entity.User;
import org.capitalmind.exception.InternalServerError;
import org.capitalmind.exception.NotFound;
import org.capitalmind.repository.StockRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class StockRepositoryImpl implements StockRepository {

    private final StockRepositoryOrm stockRepositoryOrm;

    @Override
    public Stock save(Stock stock) {
        try {
            return this.stockRepositoryOrm.save(stock);
        } catch (Exception exc) {
            throw new InternalServerError(exc);
        }
    }

    @Override
    public Stock update(Stock stock) {
        try {
            return this.stockRepositoryOrm.save(stock);
        } catch (Exception exc) {
            throw new InternalServerError(exc);
        }
    }

    @Override
    public Optional<Stock> findById(Long stockId) {
        try {
            return this.stockRepositoryOrm.findById(stockId);
        } catch (NotFound exc) {
            throw new NotFound(exc);
        } catch (Exception exc) {
            throw new InternalServerError(exc);
        }
    }

    @Override
    public void delete(Long stockId) {
        try {
            this.stockRepositoryOrm.deleteById(stockId);
        } catch (NotFound exc) {
            throw new NotFound(exc);
        } catch (Exception exc) {
            throw new InternalServerError(exc);
        }
    }

    @Override
    public List<Stock> findAllByUserData(User user) {
        try {
            return this.stockRepositoryOrm.findAllByUserData(user);
        } catch (NotFound exc) {
            throw new NotFound(exc);
        } catch (Exception exc) {
            throw new InternalServerError(exc);
        }
    }
}
