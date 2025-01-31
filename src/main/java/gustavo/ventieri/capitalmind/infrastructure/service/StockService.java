package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.stock.StockRequestDto;
import gustavo.ventieri.capitalmind.application.service.StockServiceInterface;
import gustavo.ventieri.capitalmind.domain.stock.Stock; 
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.exception.NotFoundException;
import gustavo.ventieri.capitalmind.infrastructure.persistence.StockRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService implements StockServiceInterface {


    private final StockRepository stockRepository;
    private final UserService userService;

    @Override
    public void create(StockRequestDto stockRequestDto) {

        User user =  this.userService.validateAndGetUser(stockRequestDto.userId());
       
        this.stockRepository.save( new Stock(
            null,
            stockRequestDto.name(),
            stockRequestDto.description(),
            stockRequestDto.quantity(),
            user,
            Instant.now(),
            Instant.now()
        )); 

    }

    @Override
    public void update(Long stockId, StockRequestDto stockRequestDto) {

        Stock stock = this.stockRepository.findById(stockId).orElseThrow(() -> new NotFoundException("Stock Not Found"));;

        stock.setName(stockRequestDto.name());
        stock.setDescription(stockRequestDto.description());
        stock.setQuantity(stockRequestDto.quantity());
        
        this.stockRepository.save(stock);
    }

    @Override
    public List<Stock> getAll(String userId) {

        User user =  this.userService.validateAndGetUser(userId);
        
        return this.stockRepository.findAllByUserData(user);
    }

    @Override
    public Stock getById(Long stockId) {

        return this.stockRepository.findById(stockId).orElseThrow(() -> new NotFoundException("Stock Not Found"));
    }

    @Override
    public void deleteById(Long stockId) {

        if (!this.stockRepository.existsById(stockId)) throw new NotFoundException("Stock Not Found");
        
        this.stockRepository.deleteById(stockId);
    }
    
}
