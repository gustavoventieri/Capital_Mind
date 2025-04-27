package org.capitalmind.adapter.controller;

import org.capitalmind.adapter.dto.request.ExpenseRequestImpl;
import org.capitalmind.adapter.mapper.ExpenseMapper;
import org.capitalmind.dto.request.ExpenseRequest;
import org.capitalmind.useCase.service.ExpenseServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
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
}
