package gustavo.ventieri.capitalmind.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gustavo.ventieri.capitalmind.entity.Expense;
import gustavo.ventieri.capitalmind.entity.User;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUserData(User userData);

    Optional<Expense> findByExpenseId(Long expenseId);
}
