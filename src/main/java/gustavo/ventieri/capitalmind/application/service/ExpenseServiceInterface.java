package gustavo.ventieri.capitalmind.application.service;

import java.util.List;
import java.util.Optional;

import gustavo.ventieri.capitalmind.application.dto.expense.ExpenseRequestDto;
import gustavo.ventieri.capitalmind.domain.expense.Expense;

public interface ExpenseServiceInterface {
    Boolean create(ExpenseRequestDto expenseRequestDto);

    Boolean update(Long expenseId, ExpenseRequestDto expenseRequestDto);

    Optional<List<Expense>> getAll(String userId);

    Optional<Expense> getById(Long expenseId);

    Boolean deleteById(Long expenseId);
}
