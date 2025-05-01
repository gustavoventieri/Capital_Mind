package org.capitalmind.adapter.controller;

import java.util.List;


import org.capitalmind.adapter.dto.request.StockRequestImpl;
import org.capitalmind.adapter.mapper.StockMapper;
import org.capitalmind.dto.request.StockRequest;
import org.capitalmind.dto.response.StockResponse;
import org.capitalmind.useCase.service.StockServiceImpl;
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

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/stock")
@AllArgsConstructor
public class StockController {
    private final StockServiceImpl stockServiceImpl;
    private final StockMapper stockMapper;

     @PostMapping("/create")
    public ResponseEntity<String> createExpense(@RequestBody @Valid StockRequestImpl stockRequestImpl) {
        
        StockRequest stockData = stockMapper.toDomainStockRequest(stockRequestImpl);

        this.stockServiceImpl.create(stockData);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Stock Created");
    }

     // Atualiza um despesa existente por ID
    @PutMapping("/update/{stockId}")
    public ResponseEntity<String> updateExpenseById(@PathVariable("stockId") Long stockId, @RequestBody @Valid StockRequestImpl stockRequestImpl){
        
        StockRequest stockData = stockMapper.toDomainStockRequest(stockRequestImpl);

        this.stockServiceImpl.update(stockId, stockData);
    
        return ResponseEntity.status(HttpStatus.OK).body("Stock Updated");
    }

    // Remove um despesa existente por ID
    @DeleteMapping("/delete/{stockId}")
    public ResponseEntity<String> deleteExpenseById(@PathVariable("stockId") Long stockId) {
       
        this.stockServiceImpl.deleteById(stockId);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Stock Deleted");
        
    }

     // Lista todas as despesas de um usuário por ID
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<StockResponse>> getAllExpenses(@PathVariable("userId") String userId) {

        return ResponseEntity.status(HttpStatus.OK).body(stockServiceImpl.getAll(userId));
    }

    // Lista uma despesa por ID
    @GetMapping("/{stockId}")
    public ResponseEntity<StockResponse> getExpenseById(@PathVariable("stockId") Long stockId){

     return ResponseEntity.status(HttpStatus.OK).body(stockServiceImpl.getById(stockId));          
       
    }
}
