package gustavo.ventieri.capitalmind.application.repository;

import java.util.List;

import gustavo.ventieri.capitalmind.domain.stock.Stock;
import gustavo.ventieri.capitalmind.domain.user.User;

public interface StockRepositoryInterface {
    
    List<Stock> findAllByUserData(User user);

}
