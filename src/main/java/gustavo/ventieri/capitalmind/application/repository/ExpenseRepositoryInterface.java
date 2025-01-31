package gustavo.ventieri.capitalmind.application.repository;

import java.util.List;

import gustavo.ventieri.capitalmind.domain.expense.Expense;
import gustavo.ventieri.capitalmind.domain.user.User;

public interface ExpenseRepositoryInterface {

    List<Expense> findAllByUserData(User user);
    
}
