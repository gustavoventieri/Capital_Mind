package gustavo.ventieri.capitalmind.application.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gustavo.ventieri.capitalmind.application.dto.stock.StockRequestDto;
import gustavo.ventieri.capitalmind.application.dto.stock.StockResponseDto;
import gustavo.ventieri.capitalmind.domain.stock.Stock;
import gustavo.ventieri.capitalmind.infrastructure.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {
    
    private final StockService stockService;


    @PostMapping("/create")
    public ResponseEntity<String> createStock(@RequestBody @Valid StockRequestDto stockRequestDto) {
        
        this.stockService.create(stockRequestDto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Stock Created");
    }

    @PutMapping("/update/{stockId}")
    public ResponseEntity<String> updateStockById(@PathVariable("stockId") Long stockId, @RequestBody @Valid StockRequestDto stockRequestDto){
        
        this.stockService.update(stockId, stockRequestDto);
    
        return ResponseEntity.ok().body("Stock Updated");
    }

    @DeleteMapping("/delete/{stockId}")
    public ResponseEntity<String> deleteStockById(@PathVariable("stockId") Long stockId) {
       
        stockService.deleteById(stockId);

        return ResponseEntity.ok("Stock Deleted");
       
    }


    @GetMapping("/{stockId}")
    public ResponseEntity<StockResponseDto> getStockById(@PathVariable("stockId") Long stockId){

        Stock stock = stockService.getById(stockId);
        
        return ResponseEntity.ok(
            new StockResponseDto(
                stock.getStockId(),
                stock.getName(),
                stock.getDescription(),
                stock.getQuantity(),
                stock.getQuantity() * 0.2
            ));
       
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<StockResponseDto>> getAllStocks(@PathVariable("userId") String userId) {

        List<Stock> stocks = stockService.getAll(userId);

        return ResponseEntity.ok(stocks.stream()
        .map(stock -> 
        new StockResponseDto(
            stock.getStockId(),
            stock.getName(),
            stock.getDescription(),
            stock.getQuantity(),
            stock.getQuantity() * 0.2
        ))
        .collect(Collectors.toList()));
        
    }

    



}
