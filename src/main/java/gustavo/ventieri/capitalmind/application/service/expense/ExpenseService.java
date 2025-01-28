package gustavo.ventieri.capitalmind.application.service.expense;

import java.util.List;
import java.util.Optional;

import gustavo.ventieri.capitalmind.application.dto.expense.ExpenseRequestDto;
import gustavo.ventieri.capitalmind.domain.expense.Expense;

public interface ExpenseService {

    Boolean create(ExpenseRequestDto expenseRequestDto);

    Boolean update(Long expenseId, ExpenseRequestDto expenseRequestDto);

    Optional<List<Expense>> getAll(String userId);

    Optional<Expense> getById(Long expenseId);

    Boolean deleteById(Long expenseId);
}
