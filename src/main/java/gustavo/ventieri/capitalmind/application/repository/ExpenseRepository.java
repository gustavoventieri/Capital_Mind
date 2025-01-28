package gustavo.ventieri.capitalmind.application.repository;

import java.util.List;
import java.util.Optional;

import gustavo.ventieri.capitalmind.domain.expense.Expense;
import gustavo.ventieri.capitalmind.domain.user.User;

public interface ExpenseRepository {
    List<Expense> findAllByUserData(User user);
    Optional<Expense> findById(Long expenseId);
    Expense save(Expense expense);
    void deleteById(Long expenseId);
}
