package gustavo.ventieri.capitalmind.infrastructure.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import gustavo.ventieri.capitalmind.application.dto.stock.StockRequestDto;
import gustavo.ventieri.capitalmind.application.dto.stock.StockResponseDto;
import gustavo.ventieri.capitalmind.application.service.StockServiceInterface;
import gustavo.ventieri.capitalmind.domain.stock.Stock; 
import gustavo.ventieri.capitalmind.domain.user.User;
import gustavo.ventieri.capitalmind.infrastructure.exception.NotFoundException;
import gustavo.ventieri.capitalmind.infrastructure.mapper.stock.StockMapper;
import gustavo.ventieri.capitalmind.infrastructure.clients.brapi.BrapiApi;

import gustavo.ventieri.capitalmind.infrastructure.persistence.StockRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService implements StockServiceInterface {

    private final StockRepository stockRepository;
    private final UserService userService;
    private final StockMapper stockMapper;
    private final BrapiApi brapiApi;

    @Value("${api.brapi.dev.token}")
    private String BRAPI_TOKEN;

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
    public List<StockResponseDto> getAll(String userId) {

    User user = this.userService.validateAndGetUser(userId);
    
    List<Stock> stocks = this.stockRepository.findAllByUserData(user);

    return stocks.stream()
            .map(stock -> stockMapper.toDto(stock, getTotal(stock.getQuantity(), stock.getName())))
            .collect(Collectors.toList());
}

    @Override
    public StockResponseDto getById(Long stockId) {

        Stock stock = this.stockRepository.findById(stockId).orElseThrow(() -> new NotFoundException("Stock Not Found"));

        return stockMapper.toDto(stock, getTotal(stock.getQuantity(), stock.getName()));
    }

    @Override
    public void deleteById(Long stockId) {

        if (!this.stockRepository.existsById(stockId)) throw new NotFoundException("Stock Not Found");
        
        this.stockRepository.deleteById(stockId);
    }

    @Override
    public Double getTotal(Integer quantity, String name) {
        var response = this.brapiApi.getRegularMarket(BRAPI_TOKEN, name);
        System.out.println(response);
        Double price = response.results().getFirst().regularMarketPrice();
        
        return quantity * price;

    }
    
}
