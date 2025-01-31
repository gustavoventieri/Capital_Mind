package gustavo.ventieri.capitalmind.application.service;

import java.util.List;

import gustavo.ventieri.capitalmind.application.dto.expense.ExpenseRequestDto;
import gustavo.ventieri.capitalmind.domain.expense.Expense;

public interface ExpenseServiceInterface {

    void create(ExpenseRequestDto expenseRequestDto);

    void update(Long expenseId, ExpenseRequestDto expenseRequestDto);

    List<Expense> getAll(String userId);

    Expense getById(Long expenseId);

    void deleteById(Long expenseId);
    
}
