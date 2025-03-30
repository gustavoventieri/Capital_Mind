package org.capitalmind.driver.repository.client;

import java.util.List;

import org.capitalmind.entity.Stock;
import org.capitalmind.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepositoryOrm extends JpaRepository<Stock, Long>{
    List<Stock> findAllByUserData(User user);
}
