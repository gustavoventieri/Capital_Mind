package gustavo.ventieri.capitalmind.infrastructure.persistence;

import gustavo.ventieri.capitalmind.domain.expense.Expense;
import gustavo.ventieri.capitalmind.domain.expense.ExpenseRepository;
import gustavo.ventieri.capitalmind.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface JpaExpenseRepository extends JpaRepository<Expense, Long>, ExpenseRepository {

    @Override
    List<Expense> findAllByUserData(User user);

   
}
    
