package org.capitalmind.useCase.services;

import java.util.List;

import org.capitalmind.dto.request.ExpenseRequest;
import org.capitalmind.dto.response.ExpenseResponse;
import org.capitalmind.service.ExpenseService;
import org.springframework.stereotype.Service;


@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Override
    public void create(ExpenseRequest expenseRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
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
