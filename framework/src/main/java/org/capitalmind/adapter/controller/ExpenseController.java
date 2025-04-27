package org.capitalmind.adapter.controller;

import java.util.List;

import org.capitalmind.adapter.dto.request.ExpenseRequestImpl;
import org.capitalmind.adapter.mapper.ExpenseMapper;
import org.capitalmind.dto.request.ExpenseRequest;
import org.capitalmind.dto.response.ExpenseResponse;
import org.capitalmind.useCase.service.ExpenseServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

     // Lista todas as despesas de um usuário por ID
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses(@PathVariable("userId") String userId) {

        return ResponseEntity.status(HttpStatus.FOUND).body(expenseServiceImpl.getAll(userId));
        
    }


}
