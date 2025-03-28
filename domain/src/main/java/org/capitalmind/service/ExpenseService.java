package org.capitalmind.service;

import java.util.List;

import org.capitalmind.dto.request.ExpenseRequest;
import org.capitalmind.dto.response.ExpenseResponse;

public interface ExpenseService {
    void create(ExpenseRequest expenseRequest);

    void update(Long expenseId, ExpenseRequest expenseRequest);

    List<ExpenseResponse> getAll(String userId);

    ExpenseResponse getById(Long expenseId);

    void deleteById(Long expenseId);
}
