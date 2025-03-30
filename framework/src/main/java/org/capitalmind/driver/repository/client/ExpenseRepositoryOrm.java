package org.capitalmind.driver.repository.client;

import java.util.List;

import org.capitalmind.entity.Expense;
import org.capitalmind.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepositoryOrm extends JpaRepository<Expense, Long>{
    List<Expense> findAllByUserData(User user);
}
