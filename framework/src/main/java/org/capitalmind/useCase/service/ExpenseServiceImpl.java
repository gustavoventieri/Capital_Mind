package org.capitalmind.useCase.service;

import java.time.Instant;
import java.util.List;

import org.capitalmind.driver.repository.ExpenseRepositoryImpl;
import org.capitalmind.dto.request.ExpenseRequest;
import org.capitalmind.dto.response.ExpenseResponse;
import org.capitalmind.entity.Expense;
import org.capitalmind.entity.User;
import org.capitalmind.service.ExpenseService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ExpenseServiceImpl implements ExpenseService {


    private final UserServiceImpl userServiceImpl;
    private final ExpenseRepositoryImpl expenseRepositoryImpl;

    @Override
    public void create(ExpenseRequest expenseRequest) {
       User user = userServiceImpl.validateAndGetUser(expenseRequest.userId());

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
            ));
       
    }

    @Override
    public void update(Long expenseId, ExpenseRequest expenseRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<ExpenseResponse> getAll(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public ExpenseResponse getById(Long expenseId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public void deleteById(Long expenseId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
    
}
