package org.capitalmind.adapter.controller;

import java.util.List;

import org.capitalmind.adapter.dto.request.ExpenseRequestImpl;
import org.capitalmind.adapter.mapper.ExpenseMapper;
import org.capitalmind.dto.request.ExpenseRequest;
import org.capitalmind.dto.response.ExpenseResponse;
import org.capitalmind.useCase.service.ExpenseServiceImpl;
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
@RequestMapping("/expense")
@AllArgsConstructor
public class ExpenseController {
    
    private final ExpenseServiceImpl expenseServiceImpl;
    private final ExpenseMapper expenseMapper;

      // Realiza a criação de uma despesa
    @PostMapping("/create")
    public ResponseEntity<String> createExpense(@RequestBody @Valid ExpenseRequestImpl expenseRequest) {
        
        ExpenseRequest expenseData = expenseMapper.toDomainExpenseRequest(expenseRequest);

        this.expenseServiceImpl.create(expenseData);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Expense Created");
    }

     // Atualiza um despesa existente por ID
    @PutMapping("/update/{expenseId}")
    public ResponseEntity<String> updateExpenseById(@PathVariable("expenseId") Long expenseId, @RequestBody @Valid ExpenseRequestImpl expenseRequestImpl){
        
        ExpenseRequest expenseData = expenseMapper.toDomainExpenseRequest(expenseRequestImpl);

        this.expenseServiceImpl.update(expenseId, expenseData);
    
        return ResponseEntity.status(HttpStatus.OK).body("Expense Updated");
    }

    // Remove um despesa existente por ID
    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<String> deleteExpenseById(@PathVariable("expenseId") Long expenseId) {
       
        this.expenseServiceImpl.deleteById(expenseId);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Expense Deleted");
        
    }

     // Lista todas as despesas de um usuário por ID
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses(@PathVariable("userId") String userId) {

        return ResponseEntity.status(HttpStatus.OK).body(expenseServiceImpl.getAll(userId));
    }

    // Lista uma despesa por ID
    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable("expenseId") Long expenseId){

     return ResponseEntity.status(HttpStatus.OK).body(expenseServiceImpl.getById(expenseId));          
       
    }

    


}
