package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.stock.StockRequestDto;
import gustavo.ventieri.capitalmind.application.service.StockServiceInterface;
import gustavo.ventieri.capitalmind.domain.stock.Stock; 
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.exception.InvalidDataException;
import gustavo.ventieri.capitalmind.infrastructure.exception.NotFoundException;
import gustavo.ventieri.capitalmind.infrastructure.persistence.StockRepository;
import gustavo.ventieri.capitalmind.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class StockService implements StockServiceInterface {


    private final StockRepository stockRepository;
    private final UserRepository userRepository;


    @Override
    public void create(StockRequestDto stockRequestDto) {

        String userId = stockRequestDto.userId();

        if (userId == null || userId.isEmpty()) throw new InvalidDataException("Data is Blank or Null");
        
        User user = this.userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new NotFoundException("User Not Found"));
       
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
        String userId = stockRequestDto.userId();

        if (userId == null || userId.isEmpty()) throw new InvalidDataException("Data is Blank or Null");

        this.userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new NotFoundException("User Not Found"));

        Stock stock = this.stockRepository.findById(stockId).orElseThrow(() -> new NotFoundException("Stock Not Found"));;

        stock.setName(stockRequestDto.name());
        stock.setDescription(stockRequestDto.description());
        stock.setQuantity(stockRequestDto.quantity());
        
        this.stockRepository.save(stock);
    }

    @Override
    public List<Stock> getAll(String userId) {

        if (userId == null || userId.isEmpty()) throw new InvalidDataException("Data is Blank or Null");
        
        User user = this.userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new NotFoundException("User Not Found"));;
        
        return this.stockRepository.findAllByUserData(user);
    }

    @Override
    public Stock getById(Long stockId) {
        if (stockId == null) throw new InvalidDataException("Data is Blank or Null");

        return this.stockRepository.findById(stockId).orElseThrow(() -> new NotFoundException("Expense Not Found"));
    }

    @Override
    public void deleteById(Long stockId) {

        if (!this.stockRepository.existsById(stockId)) throw new NotFoundException("Stock Not Found");
        
        this.stockRepository.deleteById(stockId);
    }
    
}
