package org.capitalmind.useCase.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.capitalmind.adapter.mapper.ExpenseMapper;
import org.capitalmind.driver.repository.ExpenseRepositoryImpl;
import org.capitalmind.dto.request.ExpenseRequest;
import org.capitalmind.dto.response.ExpenseResponse;
import org.capitalmind.entity.Expense;
import org.capitalmind.entity.User;
import org.capitalmind.exception.NotFound;
import org.capitalmind.service.ExpenseService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ExpenseServiceImpl implements ExpenseService {


    private final UserServiceImpl userServiceImpl;
    private final ExpenseRepositoryImpl expenseRepositoryImpl;
    private final ExpenseMapper expenseMapper;

    @Override
    public void create(ExpenseRequest expenseRequest) {
       User user = this.userServiceImpl.validateAndGetUser(expenseRequest.userId());

        this.expenseRepositoryImpl.save(  
            new Expense(
                null,
                expenseRequest.name(),
                expenseRequest.description(),
                expenseRequest.category(),
                expenseRequest.price(),
                user,
                Instant.now(),
                Instant.now()
            )
        );
       
    }

    @Override
    public void update(Long expenseId, ExpenseRequest expenseRequest) {
        Expense expense = this.expenseRepositoryImpl.findById(expenseId).orElseThrow(() -> new NotFound("Expense Not Found"));

        expense.setName(expenseRequest.name());
        expense.setDescription(expenseRequest.description());
        expense.setCategory(expenseRequest.category());
        expense.setPrice(expenseRequest.price());
        expense.setUpdateAt(Instant.now());

        expenseRepositoryImpl.save(expense);

    }

    @Override
    public List<ExpenseResponse> getAll(String userId) {
        User user = userServiceImpl.validateAndGetUser(userId);

        List<Expense> expenses = this.expenseRepositoryImpl.findAllByUserData(user);
        
        return expenses.stream()
            .map(expense -> expenseMapper.toExpenseResponse(expense))
            .collect(Collectors.toList());

    }

    @Override
    public ExpenseResponse getById(Long expenseId) {
        Expense expense = this.expenseRepositoryImpl.findById(expenseId)
            .orElseThrow(() -> new NotFound("Expense Not Found"));
        
        return expenseMapper.toExpenseResponse(expense);
    }

    @Override
    public void deleteById(Long expenseId) {
          // Verifica se a despesa existe
        if (this.expenseRepositoryImpl.findById(expenseId).isEmpty()) {
            throw new NotFound("Expense Not Found");
        }

        // Exclui a despesa do banco de dados
        this.expenseRepositoryImpl.delete(expenseId);
    }
    
}
